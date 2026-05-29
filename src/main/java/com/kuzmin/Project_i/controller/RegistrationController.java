package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер регистрации пользователей.
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображение формы регистрации
     */
    @GetMapping
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    /**
     * Обработка формы регистрации
     */
    @PostMapping
    public String registerUser(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}