package com.github.veloproject.application.abstractions.services;

public interface IEmailService {
    void send(String to, String subject, String message);
}
