package com.challenge.clientEnroller.entity;


import com.challenge.clientEnroller.utils.ClientStatus;
import com.challenge.clientEnroller.utils.ClientValidity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
public class Client implements Serializable {

    @Serial
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

    @Enumerated(EnumType.STRING)
    private ClientStatus clientStatus;

    @Enumerated(EnumType.STRING)
    private ClientValidity validity;

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
