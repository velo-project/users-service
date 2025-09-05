package com.github.veloproject.userservices.infrastructure.services.exceptions;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
