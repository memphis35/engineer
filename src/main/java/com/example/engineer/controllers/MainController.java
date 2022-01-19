package com.example.engineer.controllers;

import com.example.engineer.domain.User;
import com.example.engineer.repository.DepartmentCrud;
import com.example.engineer.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final DepartmentCrud departmentCrud;
    private final PasswordGenerator passwordGenerator;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("departments", departmentCrud.findAll());
        log.info("redirect to registration.html");
        return "registration";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam Map<String, String> params) {
        final User user = new User();
        user.setEmail(params.get("email"));
        user.setName(params.get("lastName"));
        user.setPassword(passwordGenerator.generateDefaultPassword());
        log.info(user.toString());
        return "redirect:/login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


}
