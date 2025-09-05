package com.github.veloproject.userservices.infrastructure.email.exceptions;

public class EmailSendingErrorException extends RuntimeException {
    public EmailSendingErrorException(String message) {
        super(message);
    }
}
