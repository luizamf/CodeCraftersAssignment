package com.challenge.clientEnroller.utils;

public enum ClientStatus {
    NEW(true),
    ENROLLED(true),
    DENIED(false);

    public final boolean value;

    ClientStatus(boolean value) {
        this.value = value;
    }
}
