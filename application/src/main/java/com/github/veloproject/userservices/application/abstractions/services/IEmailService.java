package com.github.veloproject.userservices.application.abstractions.services;

public interface IEmailService {
    void send(String to, String subject, String message);
}
