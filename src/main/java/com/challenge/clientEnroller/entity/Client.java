package com.challenge.clientEnroller.entity;


import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

//In case DB is needed.
@Entity
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

}
