package com.github.veloproject.userservices.shared.exceptions;

public class IncorrectInformationsProvided extends RuntimeException {
    public IncorrectInformationsProvided() {
        super("Error while handling request: Incorrect informations provided.");
    }
}
