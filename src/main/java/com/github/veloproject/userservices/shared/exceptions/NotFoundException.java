package com.github.veloproject.userservices.shared.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String name) {
        super(name + " was not found.");
    }
}
