package com.example.engineer.service;

import com.example.engineer.domain.EmailVerification;
import com.example.engineer.domain.User;
import com.example.engineer.repository.UserRepository;
import com.example.engineer.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final PasswordGenerator generator;
    private final MailSender mailSender;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User saveUser(final User user) {
        final String rawPassword = generator.generateDefaultPassword();
        final EmailVerification verification = new EmailVerification(user.getEmail());
        final String verifyingLink = userRepository.createVerifyingLink(verification);
        user.setPassword(encoder.encode(rawPassword));
        log.info("Password generated: {}", rawPassword);
        final User savedUser = userRepository.createUser(user);
        mailSender.sendRegistrationEmail(user.getEmail(), rawPassword, verifyingLink);
        return savedUser;
    }

    @Override
    public User findUserWithTasks(String email) {
        return userRepository.findUserWithTasks(email);
    }

    @Override
    public void verifyEmail(String email, String uuid) {
        userRepository.verifyEmail(email, uuid);
    }
}
