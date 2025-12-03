package com.github.veloproject.userservices.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String name) {
        super(name + " não foi encontrado(a).");
    }
}
