package com.example.engineer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String redirect(Authentication authentication) {
        return authentication == null
                ? "redirect:/loginPage"
                : "redirect:/engineer";
    }
}
