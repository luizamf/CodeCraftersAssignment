package com.challenge.clientEnroller.utils;

import com.challenge.clientEnroller.dto.ClientDTO;

public class ExternalApplication {

    //This method is a placeholder for a call to an external system
    public static int getClientReputation(ClientDTO client) {
        int min = 0;
        int max = 200;
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
