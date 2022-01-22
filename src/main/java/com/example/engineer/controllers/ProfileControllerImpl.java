package com.example.engineer.controllers;

import com.example.engineer.domain.User;
import com.example.engineer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/engineer/profile")
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final UserRepository repository;
    private final Map<Long, User> cachedUsers = new ConcurrentHashMap<>();

    @Override
    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String getProfile(@PathVariable Long id, Authentication userDetails, Model model) {
        if (this.canPerformOperation(userDetails, id)) {
            model.addAttribute("hasError", false);
            model.addAttribute("user", cachedUsers.remove(id));
        } else {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Не хватает прав для просмотра пользователя #" + id);
        }
        return "profile";
    }

    @Override
    @PostMapping("/{id}")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String updateProfile(@PathVariable Long id,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                Authentication userDetails, Model model) {
        if (this.canPerformOperation(userDetails, id)) {
            User userToUpdate = cachedUsers.remove(id);
            userToUpdate.setName(firstName + " " + lastName);
            repository.createUser(userToUpdate);
            model.addAttribute("hasError", false);
            model.addAttribute("user", userToUpdate);
        } else {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Не хватает прав для редактирования пользователя #" + id);
        }
        return "profile";
    }

    @Override
    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String deleteProfile(@PathVariable Long id) {
        return "profile";
    }

    private boolean canPerformOperation(Authentication userDetails, Long id) {
        final User loggedUser = repository.findUserByEmail(userDetails.getName());
        final boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        final boolean canBeLoad = Objects.equals(loggedUser.getId(), id);
        if (canBeLoad || isAdmin) cachedUsers.put(id, loggedUser);
        return canBeLoad || isAdmin;
    }
}
