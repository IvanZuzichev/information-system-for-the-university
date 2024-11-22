package com.mpt.journal.controller;

import com.mpt.journal.model.UserModel;
import com.mpt.journal.repository.UserReposotory;
import com.mpt.journal.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserReposotory userReposotory;

    @GetMapping("/registration")
    public String registartion(Model model) {
//        List<UserModel> users = userService.findAllUsers();
//        model.addAttribute("users", users);

        return "registration";
    }
    @PostMapping("/registration")
    public String addregistration(UserModel user, Map<String, Object> model) {
        UserModel userFromDB = userReposotory.findByUsername(user.getUsername());

        // Проверьте, существует ли пользователь
        if (userFromDB != null) {
            model.put("message", "Пользователь уже существует!");
            return "registration";
        }

        // Проверьте длину пароля
        if (user.getPassword().length() < 3) {
            model.put("message", "Пароль должен быть не менее 3 символов!");
            return "registration"; // Верните на страницу регистрации с сообщением об ошибке
        }

        user.setRole("user");
        user.setDeleted(false);
        userReposotory.save(user);

        return "redirect:/login";
    }
}
