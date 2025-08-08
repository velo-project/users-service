package com.github.veloproject.infrastructure.email;

import com.github.veloproject.application.abstractions.services.IEmailService;
import com.github.veloproject.infrastructure.email.exceptions.EmailSendingErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String message) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(message);
            mailSender.send(msg);
        } catch (Exception e) {
            throw new EmailSendingErrorException(e.getMessage());
        }
    }
}
