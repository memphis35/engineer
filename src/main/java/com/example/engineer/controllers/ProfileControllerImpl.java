package com.example.engineer.controllers;

import com.example.engineer.domain.Role;
import com.example.engineer.domain.User;
import com.example.engineer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public String getProfile(@PathVariable Long id, Authentication userDetails, Model model) {
        final boolean hasAccess = this.canPerformOperation(userDetails, id);
        model.addAttribute("hasAccess", hasAccess);
        if (!hasAccess) {
            model.addAttribute("error",
                    "Не хватает прав для просмотра пользователя");
        }
        model.addAttribute("user", cachedUsers.remove(id));
        return "profile";
    }

    @Override
    @PostMapping(value = "/{id}")
    public String updateProfile(@PathVariable Long id,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                Authentication userDetails, Model model) {
        final boolean hasAccess = this.canPerformOperation(userDetails, id);
        model.addAttribute("hasAccess", hasAccess);
        final User userToUpdate = cachedUsers.remove(id);
        userToUpdate.setName(firstName + " " + lastName);
        repository.createUser(userToUpdate);
        model.addAttribute("user", userToUpdate);
        if (!hasAccess) {
            model.addAttribute("error",
                    "Не хватает прав для редактирования пользователя");
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
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ROLE_READ_ONLY.name()));
        final boolean canBeLoad = Objects.equals(loggedUser.getId(), id);
        cachedUsers.put(id, loggedUser);
        return canBeLoad && isUser;
    }

    @Override
    public String updateEmail(Long id, String newEmail) {
        return "engineer";
    }

    @Override
    public String updatePassword(Long id, String oldPassword, String newPassword) {
        return "engineer";
    }
}
