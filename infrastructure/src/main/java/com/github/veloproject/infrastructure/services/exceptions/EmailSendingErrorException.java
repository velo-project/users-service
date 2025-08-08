package com.github.veloproject.infrastructure.services.exceptions;

public class EmailSendingErrorException extends RuntimeException {
    public EmailSendingErrorException(String message) {
        super(message);
    }
}
