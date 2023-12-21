package com.challenge.clientEnroller.service;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.dto.builder.ClientMapper;
import com.challenge.clientEnroller.repository.ClientEnrollRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ClientEnrollServiceTest {
    @InjectMocks
    ClientEnrollService clientEnrollService;

    @Mock
    ClientEnrollRepository clientEnrollRepository;

    @Test
    public void checkExpiredClientID() {
        ClientDTO client = new ClientDTO("name", "lastName", "XT123456", "1234567891234", LocalDate.parse("2014-12-17"), LocalDate.parse("2022-12-17"));
        assertEquals(clientEnrollService.checkClient(client), new Pair<>(Boolean.FALSE, "Client ID is expired"));
    }

    @Test
    public void checkExistingClient() {
        ClientDTO client = new ClientDTO("name", "lastName", "XT123456", "4234567891236", LocalDate.parse("2014-12-17"), LocalDate.parse("2024-12-17"));
        when(clientEnrollRepository.getByDocumentID("XT123456")).thenReturn(ClientMapper.toEntity(client));
        assertEquals(clientEnrollService.checkClient(client), new Pair<>(Boolean.FALSE, "Client does already exist"));
    }

    @RepeatedTest(10)
    public void checkClient() {
        ClientDTO client = new ClientDTO("name", "lastName", "XT123456", "6234567891234", LocalDate.parse("2014-12-17"), LocalDate.parse("2024-12-17"));
        List<Pair<Boolean, String>> possibleResponses = Arrays.asList(
                new Pair<>(Boolean.TRUE, "Candidate with no risk"),
                new Pair<>(Boolean.TRUE, "Candidate with medium risk, but enrollment still possible"),
                new Pair<>(Boolean.FALSE, "Risky candidate, enrollment not acceptable"));
        assertTrue(possibleResponses.contains(clientEnrollService.checkClient(client)));
    }
}
