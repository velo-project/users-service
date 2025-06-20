package com.github.veloproject.userservices.shared.exceptions;

public class EmailSendingErrorException extends RuntimeException {
    public EmailSendingErrorException(String message) {
        super(message);
    }
}
