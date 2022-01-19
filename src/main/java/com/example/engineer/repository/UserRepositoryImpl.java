package com.example.engineer.repository;

import com.example.engineer.domain.User;
import com.example.engineer.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User findUser(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void createUser(User newUser) {
        final User user = userJpaRepository.save(newUser);
        log.info("User with username");
    }

    @Override
    public boolean userExists(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userJpaRepository.findUserByEmail(email);
    }
}
