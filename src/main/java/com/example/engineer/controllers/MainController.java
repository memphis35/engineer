package com.example.engineer.controllers;

import com.example.engineer.domain.Department;
import com.example.engineer.domain.User;
import com.example.engineer.repository.DepartmentCrud;
import com.example.engineer.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Set;

@Controller
@Validated
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final Set<String> registerParams = Set.of("firstName", "lastName", "email", "department");

    private final DepartmentCrud departmentCrud;
    private final PasswordGenerator passwordGenerator;

    //@GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("departments", departmentCrud.findAll());
        log.info("redirect to registration.html");
        return "registration";
    }

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
        user.setPassword(passwordGenerator.generateDefaultPassword());
        log.info(user.toString());
        return "redirect:/loginPage";
    }

    @GetMapping(path = "/loginPage")
    public String login(Model model) {
        model.addAttribute("departments", departmentCrud.findAll());
        return "login";
    }


}
