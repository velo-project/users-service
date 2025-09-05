package com.github.veloproject.userservices.domain.exceptions;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }
}
