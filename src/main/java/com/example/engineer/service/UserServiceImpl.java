package com.example.engineer.service;

import com.example.engineer.domain.User;
import com.example.engineer.repository.UserJpaRepository;
import com.example.engineer.repository.UserRepository;
import com.example.engineer.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder encoder;
    private final PasswordGenerator generator;
    private final MailSender mailSender;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User saveUser(final User user) {
        final String password = generator.generateDefaultPassword();
        user.setPassword(encoder.encode(password));
        final User savedUser = userJpaRepository.save(user);
        mailSender.sendRegistrationEmail(user.getEmail());
        return savedUser;
    }

    @Override
    public User findUserWithTasks(String email) {
        return userJpaRepository.findUserWithTasks(email);
    }
}
