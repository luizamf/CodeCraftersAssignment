package com.challenge.clientEnroller.service;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.dto.builder.ClientMapper;
import com.challenge.clientEnroller.entity.Client;
import com.challenge.clientEnroller.repository.ClientEnrollRepository;
import com.challenge.clientEnroller.utils.ClientStatus;
import com.challenge.clientEnroller.utils.ClientValidity;
import com.challenge.clientEnroller.utils.DocumentGenerator;
import com.challenge.clientEnroller.utils.ExternalApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ValueRange;

@Service
public class ClientEnrollService {
    private final ClientEnrollRepository clientEnrollRepository;

    @Autowired
    public ClientEnrollService(ClientEnrollRepository clientEnrollRepository) {
        this.clientEnrollRepository = clientEnrollRepository;
    }

    public String checkClient(ClientDTO client) {
        if (clientEnrollRepository.getByDocumentID(client.getDocumentID()) != null) {
            return "Client does already exist";
        }

        Client clientEntity = ClientMapper.toEntity(client);
        if (client.getExpiryDate().isBefore(LocalDate.now())) {
            return saveClient(clientEntity, ClientValidity.INVALID, "Client ID is expired");
        }
        return checkClientReputation(ExternalApplication.getClientReputation(client), clientEntity);
    }

    public String generateDocument(String documentID) {
        Client client = clientEnrollRepository.getByDocumentID(documentID);
        if (client == null) {
            return "Please check the client first";
        }

        if (!ClientStatus.NEW.equals(client.getClientStatus())) {
            return "Document already generated";
        }

        ClientDTO clientDTO = ClientMapper.toDTO(client);
        if (ClientValidity.VALID.equals(client.getValidity())) {
            DocumentGenerator.generatePdfDocument("Enrollment Document", clientDTO);
            client.setClientStatus(ClientStatus.ENROLLED);
            clientEnrollRepository.save(client);
            return "Enrollment document generated";
        } else {
            DocumentGenerator.generatePdfDocument("Denial Document", clientDTO);
            client.setClientStatus(ClientStatus.DENIED);
            clientEnrollRepository.save(client);
            return "Denial document generated";
        }
    }

    private String checkClientReputation(int clientReputation, Client clientEntity) {
        if (ValueRange.of(0, 20).isValidIntValue(clientReputation)) {
            return saveClient(clientEntity, ClientValidity.VALID, "Candidate with no risk");
        }
        if (ValueRange.of(21, 99).isValidIntValue(clientReputation)) {
            return saveClient(clientEntity, ClientValidity.VALID, "Candidate with medium risk, but enrollment still possible");
        }
        return saveClient(clientEntity, ClientValidity.INVALID, "Risky candidate, enrollment not acceptable");
    }

    private String saveClient(Client clientEntity, ClientValidity validity, String message) {
        clientEntity.setClientStatus(ClientStatus.NEW);
        clientEntity.setValidity(validity);
        clientEnrollRepository.save(clientEntity);
        return message;
    }
}
