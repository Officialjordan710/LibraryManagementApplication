package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String link = "http://localhost:8080/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Library Account Verification");
        message.setText("Click to verify your email: " + link);

        mailSender.send(message);
    }
}
