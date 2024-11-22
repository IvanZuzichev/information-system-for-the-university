package com.mpt.journal.controller;


import com.mpt.journal.model.UserModel;
import jakarta.transaction.Transactional;
import com.mpt.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Transactional
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        List<UserModel> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "login";
    }

    @GetMapping("/redirect")
    public String redirectToRolePage(Model model) {
        UserModel currentUser  = userService.getCurrentUser ();
        // Проверяем роль и перенаправляем на соответствующую страницу
        if ("admin".equals(currentUser .getRole())) {
            return "redirect:/events"; // Путь к странице для администраторов
        } else if ("worker".equals(currentUser .getRole())) {
            return "redirect:/teachers"; // Путь к странице для работников
        } else if ("user".equals(currentUser .getRole())) {
            return "redirect:/users"; // Путь к странице для пользователей
        }

        // Если роль не совпадает, перенаправляем на страницу по умолчанию (например, на главную)
        return "redirect:/";
    }
}