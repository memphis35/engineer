package com.example.engineer.service;

public interface MailSender {
    void sendRegistrationEmail(String email, String password, String link);
}
