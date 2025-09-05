package com.github.veloproject.userservices.domain.exceptions;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
