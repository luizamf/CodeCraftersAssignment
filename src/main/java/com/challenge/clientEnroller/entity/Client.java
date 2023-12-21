package com.challenge.clientEnroller.entity;


import com.challenge.clientEnroller.utils.ClientStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    private UUID id;
    private String firstName;
    private String lastName;
    private String documentID;
    private String CNP;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private ClientStatus clientStatus;

    public Client(String firstName, String lastName, String documentID, String CNP, LocalDate startDate, LocalDate expiryDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentID = documentID;
        this.CNP = CNP;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public Client() {

    }
}
