package com.github.veloproject.userservices.shared.emails;

import com.github.veloproject.userservices.shared.exceptions.EmailSendingErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMail(String to, String subject, String message) {
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
