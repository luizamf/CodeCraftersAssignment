package com.challenge.clientEnroller.dto.builder;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    ClientDTO toClientDTO(Client client);

    Client toEntity(ClientDTO caregiverDetailsDTO);
}
