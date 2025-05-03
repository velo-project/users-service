package com.github.veloproject.userservices.shared.exceptions;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String parameter) {
        super("Error while handling request: Parameter \"" + parameter + "\" invalid.");
    }
}
