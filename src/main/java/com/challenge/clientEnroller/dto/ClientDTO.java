package com.challenge.clientEnroller.dto;

import com.challenge.clientEnroller.validator.CNPConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Pattern(regexp = "[A-Z]{2}[0-9]{6}", message = "Invalid document number.")
    private String documentID;
    @CNPConstraint
    private String CNP;
    private LocalDate startDate;
    private LocalDate expiryDate;
}
