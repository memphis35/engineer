package com.example.engineer.controllers;

import com.example.engineer.domain.*;
import com.example.engineer.repository.DepartmentCrud;
import com.example.engineer.repository.TaskCrudRepository;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Validated
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final DepartmentCrud departmentCrud;
    private final UserService userService;
    private final TaskCrudRepository taskCrudRepository;

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
        return "redirect:/login";
    }

    @GetMapping(path = "/login")
    public String login(Model model, @RequestParam(defaultValue = "false") String error) {
        if (error.equals("true")) model.addAttribute("isError", true);
        model.addAttribute("departments", departmentCrud.findAll());
        return "login";
    }

    @GetMapping(path = "/verifyEmail")
    public String verifyEmail(@RequestParam String email,
                              @RequestParam String uuid) {
        userService.verifyEmail(email, uuid);
        return "redirect:/login";
    }

    @GetMapping(path = "/engineer")
    public String home(Model model, Authentication authentication) {
        final User user = userService.findUserWithTasks(authentication.getName());
        final Collection<Task> tasks = user.getRole() != Role.ROLE_READ_ONLY
                ? user.getTasks()
                : taskCrudRepository.findAll();
        final Map<String, List<Task>> groupedTasks = tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getStatus().name()));
        final Long attentionTasks = tasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .filter(task -> task.getExpirationDate().minusDays(14).isBefore(LocalDate.now()))
                .count();
        final Long expiredTasks = tasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .filter(task -> task.getExpirationDate().isBefore(LocalDate.now()))
                .count();
        model.addAttribute("user", user);
        model.addAttribute("taskCount", tasks.size());
        model.addAttribute("tasks", groupedTasks);
        model.addAttribute("requiresAttention", attentionTasks);
        model.addAttribute("expired", expiredTasks);
        return "home";
    }
}
