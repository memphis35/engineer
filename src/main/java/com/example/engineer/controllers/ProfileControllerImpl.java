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
        final boolean hasAccess = this.canPerformOperation(userDetails, id);
        model.addAttribute("hasAccess", hasAccess);
        if (!hasAccess) {
            model.addAttribute("error",
                    "Не хватает прав для просмотра/редактирования пользователя #" + id);
        }
        model.addAttribute("user", cachedUsers.remove(id));
        return "profile";
    }

    @Override
    @PostMapping("/{id}")
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
    public String deleteProfile(@PathVariable Long id) {
        return "forward:login" + id;
    }

    private boolean canPerformOperation(Authentication authentication, Long id) {
        final User loggedUser = repository.findUserByEmail(authentication.getName());
        final boolean isUser = authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_READ_ONLY"));
        final boolean canBeLoad = Objects.equals(loggedUser.getId(), id);
        cachedUsers.put(id, loggedUser);
        return canBeLoad && isUser;
    }
}
