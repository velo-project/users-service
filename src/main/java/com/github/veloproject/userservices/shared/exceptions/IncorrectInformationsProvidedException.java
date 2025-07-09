package com.github.veloproject.userservices.shared.exceptions;

public class IncorrectInformationsProvidedException extends RuntimeException {
    public IncorrectInformationsProvidedException() {
        super("Error while handling request: Incorrect informations provided.");
    }
}
