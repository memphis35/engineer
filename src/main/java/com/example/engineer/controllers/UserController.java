package com.example.engineer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface UserController {

    public String get(Model model, Authentication authentication);


}
