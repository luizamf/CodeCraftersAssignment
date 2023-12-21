package com.challenge.clientEnroller.service;

import com.challenge.clientEnroller.utils.ClientStatus;
import com.challenge.clientEnroller.utils.ExternalApplication;
import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.dto.builder.ClientMapper;
import com.challenge.clientEnroller.entity.Client;
import com.challenge.clientEnroller.repository.ClientEnrollRepository;
import org.antlr.v4.runtime.misc.Pair;
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

    public Pair<Boolean, String> checkClient(ClientDTO client) {
        if (clientEnrollRepository.getByDocumentID(client.getDocumentID()) != null) {
            return new Pair<>(false, "Client does already exist");
        }

        Client clientEntity = ClientMapper.toEntity(client);
        if (client.getExpiryDate().isBefore(LocalDate.now())) {
            return saveClient(clientEntity, ClientStatus.DENIED, "Client ID is expired");
        }
        return checkClientReputation(ExternalApplication.getClientReputation(client), clientEntity);
    }

    private Pair<Boolean, String> checkClientReputation(int clientReputation, Client clientEntity) {
        Pair<ClientStatus, String> status = new Pair<>(ClientStatus.DENIED, "Risky candidate, enrollment not acceptable");
        if (ValueRange.of(0, 20).isValidIntValue(clientReputation)) {
            status = new Pair<>(ClientStatus.NEW, "Candidate with no risk");
        }
        if (ValueRange.of(21, 99).isValidIntValue(clientReputation)) {
            status = new Pair<>(ClientStatus.NEW, "Candidate with medium risk, but enrollment still possible");
        }
        return saveClient(clientEntity, status.a, status.b);
    }

    private Pair<Boolean, String> saveClient(Client clientEntity, ClientStatus status, String message) {
        clientEntity.setClientStatus(status);
        clientEnrollRepository.save(clientEntity);
        return new Pair<>(status.value, message);
    }
}
