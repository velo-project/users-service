package com.github.veloproject.userservices.shared.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String name) {
        super(name + " already exists.");
    }
}
