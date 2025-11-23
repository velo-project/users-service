package com.github.veloproject.userservices.application.abstractions.services;

import java.util.Map;

public interface IEmailService {
    void send(String to, String subject, String message);
    void sendWithTemplate(String to, String subject, String htmlTemplate, Map<String, String> variables);
}
