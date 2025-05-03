package com.github.veloproject.userservices.shared.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String name) {
        super("Error while handling request: " + name + " already exists.");
    }
}
