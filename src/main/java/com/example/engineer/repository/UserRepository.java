package com.example.engineer.repository;

import com.example.engineer.domain.User;

public interface UserRepository {

    public User findUser(Long id);

    void createUser(User newUser);

    boolean userExists(String email);

    User findUserByEmail(String username);

    User findUserByEmailWithAuths(String username);
}
