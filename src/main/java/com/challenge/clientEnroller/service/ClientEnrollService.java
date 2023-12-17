package com.challenge.clientEnroller.service;

import com.challenge.clientEnroller.dto.ClientDTO;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ValueRange;

@Service
public class ClientEnrollService {

    public Pair<Boolean, String> checkClient(ClientDTO client) {
        if (isIDNotValid(client.getExpiryDate())) {
            return new Pair<>(Boolean.FALSE, "Client ID is expired");
        }
        if (isAnExistingClient(client)) {
            return new Pair<>(Boolean.FALSE, "Client does already exist");
        }
        return checkClientReputation(getClientReputation(client));
    }

    //This method is a placeholder for a call to an external system
    private boolean isAnExistingClient(ClientDTO client) {
        return client.getCNP().charAt(0) < '5';
    }

    private Pair<Boolean, String> checkClientReputation(int clientReputation) {
        if (ValueRange.of(0, 20).isValidIntValue(clientReputation)) {
            return new Pair<>(Boolean.TRUE, "Candidate with no risk");
        } else {
            if (ValueRange.of(21, 99).isValidIntValue(clientReputation)) {
                return new Pair<>(Boolean.TRUE, "Candidate with medium risk, but enrollment still possible");
            } else {
                return new Pair<>(Boolean.FALSE, "Risky candidate, enrollment not acceptable");
            }
        }
    }

    private int getClientReputation(ClientDTO client) {
        return generateRandomReputation(0, 200);
    }

    //This method is a placeholder for a call to an external system
    private int generateRandomReputation(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    private boolean isIDNotValid(LocalDate expiryDate) {
        return expiryDate.isBefore(LocalDate.now());
    }
}
