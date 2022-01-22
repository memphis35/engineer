package com.example.engineer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface ProfileController {

    String getProfile(Long id, Authentication userDetails, Model model);

    String updateProfile(Long id, String firstName, String lastName, Authentication userDetails, Model model);

    String deleteProfile(Long id);
}
