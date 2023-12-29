package com.challenge.clientEnroller.utils;

import com.challenge.clientEnroller.dto.ClientDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class DocumentGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DocumentGenerator.class);
    private static final String PDF = ".pdf";
    private static final String UNDERLINE = "_";
    private static int documentNumber = 0;

    public static void generatePdfDocument(String title, ClientDTO client) {
        PDDocument document = new PDDocument();
        try {
            addDocumentContent(document, title, client);
            documentNumber++;
            document.save(title + UNDERLINE + documentNumber + PDF);
            document.close();
        } catch (IOException e) {
            logger.error("Couldn't create document.");
            e.printStackTrace();
        }
    }

    private static void addDocumentContent(PDDocument document, String title, ClientDTO client) {
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            float startY = page.getCropBox().getUpperRightY() - 100;
            float startX = page.getCropBox().getLowerLeftX() + 50;
            float endX = page.getCropBox().getUpperRightX() - 50;

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 20);
            contentStream.newLineAtOffset((endX - startX) / 2 - 65, startY);
            contentStream.showText(title);
            contentStream.endText();

            String[] content = WordUtils.wrap(readContent(client, title), (int) (endX / 7)).split("\\r?\\n");
            for (int i = 0; i < content.length; i++) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 14);
                contentStream.newLineAtOffset(startX, 600 - i * 15);
                contentStream.showText(content[i]);
                contentStream.endText();
            }
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 14);
            contentStream.newLineAtOffset(startX, 100);
            contentStream.showText("Date: " + LocalDate.now());
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 14);
            contentStream.newLineAtOffset(startX, 80);
            contentStream.showText("Bank representative signature:");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 14);
            contentStream.newLineAtOffset(endX - 100, 80);
            contentStream.showText("Client signature:");
            contentStream.endText();

        } catch (IOException e) {
            logger.error("Couldn't add content to the document.");
            e.printStackTrace();
        }
    }

    private static String readContent(ClientDTO client, String title) {
        URL url = DocumentGenerator.class.getClassLoader().getResource("documentTemplate.txt");
        if (url != null) {
            try {
                Path path = Path.of(url.getPath().substring(1));
                String content = new String(Files.readAllBytes(path));
                return String.format(content, client.getLastName(), client.getFirstName(), client.getDocumentID(), client.getCNP(), getDocumentType(title));
            } catch (IOException e) {
                logger.error("Couldn't find template file.");
                e.printStackTrace();
            }
        }
        return StringUtils.EMPTY;
    }

    private static String getDocumentType(String title) {
        return title.contains("Enrollment") ? "accepted" : "denied";
    }
}
