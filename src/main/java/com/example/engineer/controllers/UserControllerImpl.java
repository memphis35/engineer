package com.example.engineer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/engineer")
public class UserControllerImpl implements UserController {

    @Override
    @GetMapping
    public String get(Model model, Authentication authentication) {
        model.addAttribute("userDetail", authentication.getName());
        return "home";
    }
}
