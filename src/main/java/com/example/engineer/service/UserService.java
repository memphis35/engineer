package com.example.engineer.service;

import com.example.engineer.domain.User;

public interface UserService {

    User saveUser(User user);

    User findUserWithTasks(String name);

    void verifyEmail(String email, String uuid);
}
