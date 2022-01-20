package com.example.engineer.repository;

import com.example.engineer.domain.User;

public interface UserRepository {

    User findUser(Long id);

    User createUser(User newUser);

    boolean userExists(String email);

    User findUserByEmail(String username);
}
