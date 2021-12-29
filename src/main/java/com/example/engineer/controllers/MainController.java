package com.example.engineer.controllers;

import com.example.engineer.domain.Authority;
import com.example.engineer.domain.User;
import com.example.engineer.repository.AuthoritiesCrudRepository;
import com.example.engineer.repository.DepartmentCrudRepository;
import com.example.engineer.utils.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class MainController {

    private final DepartmentCrudRepository departmentCrudRepository;
    private final AuthoritiesCrudRepository authoritiesCrudRepository;
    private final PasswordGenerator passwordGenerator;
    private final Map<String, Authority> cachedAuthorities = new HashMap<>(5);

    @Autowired
    public MainController(DepartmentCrudRepository departmentCrudRepository,
                          AuthoritiesCrudRepository authoritiesCrudRepository,
                          PasswordGenerator passwordGenerator) {
        this.departmentCrudRepository = departmentCrudRepository;
        this.authoritiesCrudRepository = authoritiesCrudRepository;
        this.passwordGenerator = passwordGenerator;
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("roles", authoritiesCrudRepository.findAll());
        model.addAttribute("departments", departmentCrudRepository.findAll());
        log.info("redirect to registration.html");
        return "registration";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam Map<String, String> params) {
        this.refreshAuthorities();
        final User user = new User();
        user.setEmail(params.get("email"));
        user.setName(params.get("lastName"));
        user.setPassword(passwordGenerator.generateDefaultPassword());
        log.info(user.toString());
        return "redirect:/login";
    }

    private void refreshAuthorities() {
        cachedAuthorities.clear();
        authoritiesCrudRepository.findAll()
                .forEach(authority -> cachedAuthorities.put(authority.getAuthority(), authority));
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


}
