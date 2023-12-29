package com.challenge.clientEnroller.dto.builder;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.entity.Client;


public class ClientMapper {

    public static Client toEntity(ClientDTO clientDTO) {
        return new Client(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getDocumentID(), clientDTO.getCNP(), clientDTO.getStartDate(), clientDTO.getExpiryDate());
    }

    public static ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getFirstName(), client.getLastName(), client.getDocumentID(), client.getCNP(), client.getStartDate(), client.getExpiryDate());
    }
}
