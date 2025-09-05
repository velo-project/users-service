package com.github.veloproject.userservices.domain.exceptions;

public class EmailSendingErrorException extends RuntimeException {
    public EmailSendingErrorException(String message) {
        super(message);
    }
}
