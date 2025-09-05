package com.github.veloproject.userservices.domain.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String name) {
        super(name + " already exists.");
    }
}
