package com.challenge.clientEnroller.dto.builder;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.entity.Client;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientMapper {

    public Client toEntity(ClientDTO clientDTO) {
        return new Client(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getDocumentID(), clientDTO.getCNP(), clientDTO.getStartDate(), clientDTO.getExpiryDate());
    }
}
