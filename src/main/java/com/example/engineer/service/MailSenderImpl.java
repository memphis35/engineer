package com.example.engineer.service;

import com.sun.mail.smtp.SMTPMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderImpl implements MailSender {

    private final JavaMailSender emailSender;

    @Override
    public void sendRegistrationEmail(String email, String password) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Подтверждение регистрации");
        message.setText(String.format("Username: %s\nPassword: %s", email, password));
        emailSender.send(message);
        log.info("Mail has been sent to {}", email);
    }
}
