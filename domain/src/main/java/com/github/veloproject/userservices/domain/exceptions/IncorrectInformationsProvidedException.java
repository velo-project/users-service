package com.github.veloproject.userservices.domain.exceptions;

public class IncorrectInformationsProvidedException extends RuntimeException {
    public IncorrectInformationsProvidedException() {
        super("Erro ao manipular request: Informações incorretas foram passadas.");
    }
}
