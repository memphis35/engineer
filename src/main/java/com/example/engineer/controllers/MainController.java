package com.example.engineer.controllers;

import com.example.engineer.domain.Department;
import com.example.engineer.domain.User;
import com.example.engineer.repository.DepartmentCrud;
import com.example.engineer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Controller
@Validated
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final DepartmentCrud departmentCrud;
    private final UserService userService;

    @PostMapping(path = "/register")
    public String register(@RequestParam @NotBlank String firstName,
                           @RequestParam @NotBlank String lastName,
                           @RequestParam @Pattern(regexp = "^[a-zA-Z._]{3,}[0-9]{0,3}@[a-zA-Z0-9]{3,}.com$") String email,
                           @RequestParam @Positive Long departmentId) {
        final Department department = departmentCrud.findById(departmentId).orElseThrow();
        final User user = new User();
        user.setEmail(email);
        user.setName(String.format("%s %s", lastName, firstName));
        user.setDepartment(department);
        log.info("User {} from {} has been created", email, department.getName());
        userService.saveUser(user);
        return "redirect:/loginPage";
    }

    @GetMapping(path = "/loginPage")
    public String login(Model model) {
        model.addAttribute("departments", departmentCrud.findAll());
        return "login";
    }

    @GetMapping(path = "/verifyEmail")
    public String verifyEmail(@RequestParam String email,
                              @RequestParam String uuid) {
        userService.verifyEmail(email, uuid);
        return "redirect:/loginPage";
    }

    @GetMapping(path = "/engineer")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String home(Model model, Authentication authentication) {
        final User user = userService.findUserWithTasks(authentication.getName());
        model.addAttribute("user", user);
        return "home";
    }
}
