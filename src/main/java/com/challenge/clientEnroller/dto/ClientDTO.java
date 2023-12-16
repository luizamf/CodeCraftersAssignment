package com.challenge.clientEnroller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDTO {
    private String firstName;
    private String lastName;
    private String documentID;
    private String CNP;
    private LocalDate startDate;
    private LocalDate expiryDate;
}
