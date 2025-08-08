package com.github.veloproject.infrastructure.email.exceptions;

public class EmailSendingErrorException extends RuntimeException {
    public EmailSendingErrorException(String message) {
        super(message);
    }
}
