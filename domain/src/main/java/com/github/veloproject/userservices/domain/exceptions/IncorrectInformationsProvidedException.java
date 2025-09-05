package com.github.veloproject.userservices.domain.exceptions;

public class IncorrectInformationsProvidedException extends RuntimeException {
    public IncorrectInformationsProvidedException() {
        super("Error while handling request: Incorrect informations provided.");
    }
}
