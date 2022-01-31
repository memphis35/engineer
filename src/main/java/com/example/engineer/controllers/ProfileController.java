package com.example.engineer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProfileController {

    String getProfile(Long id, Authentication userDetails, Model model);

    String updateProfile(Long id, String firstName, String lastName, Authentication userDetails, Model model);

    String deleteProfile(Long id);

    String updateEmail(Long id, String newEmail);

    String updatePassword(Long id, String oldPassword, String newPassword, Authentication userDetails, Model model);
}
