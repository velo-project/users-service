package com.github.veloproject.userservices.shared.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String name) {
        super("Error while handling request: " + name + " was not found.");
    }
}
