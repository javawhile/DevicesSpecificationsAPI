package org.devices.specifications.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.sender}")
    private String sender;

    public void sendMail() {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo("hardikgoel19@gmail.com");
            mailMessage.setText("Test Body: " + new Date());
            mailMessage.setSubject("Test Subject");
            javaMailSender.send(mailMessage);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}