package com.github.veloproject.userservices.infrastructure.email;

import com.github.veloproject.userservices.application.abstractions.services.IEmailService;
import com.github.veloproject.userservices.infrastructure.email.exceptions.EmailSendingErrorException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public void sendWithTemplate(String to, String subject, String htmlTemplate, Map<String, String> variables) {
        try {
            String htmlContent = htmlTemplate;
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailSendingErrorException(e.getMessage());
        }
    }
}
