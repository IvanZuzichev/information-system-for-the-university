package com.mpt.journal.controller;


import com.mpt.journal.model.UserModel;
import com.mpt.journal.service.UserService; // Предполагается, что у вас есть UserService
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Transactional
public class UserController {

    @Autowired
    private UserService userService; // Сервис для работы с пользователями

    @GetMapping("/users")
    public String getAllUsers(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int pageSize) {

        List<UserModel> users = userService.findAllUsers();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, users.size());

        model.addAttribute("users", users.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) users.size() / pageSize));

        return "userList"; // Переход к странице отображения пользователей
    }
    @PostMapping("/users/add")
    public String addUser (@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role) {
        UserModel newUser  = new UserModel(0, username,password, role, false);
        userService.addUser(newUser);
        return "redirect:/users";
    }

    @PostMapping("/users/update")
    public String updateUser (@RequestParam int id,
                              @RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String role) {
        UserModel updatedUser  = new UserModel(id, username, password, role, false);
        userService.updateUser(updatedUser);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser (@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/users/delete-all")
    public String deleteAllUsers() {
        userService.deleteUserDeletedIdTrue();
        return "redirect:/users";
    }

    @GetMapping("/users/search")
    public String searchUsers(@RequestParam(required = false) String username,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int pageSize,
                              Model model) {

        List<UserModel> users;

        if (username != null && !username.isEmpty()) {
            users = userService.findUsersByUsername(username); // Метод для поиска пользователей по имени
        } else {
            users = userService.findAllUsers();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, users.size());
        model.addAttribute("users", users.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) users.size() / pageSize));

        // Фильтр
        model.addAttribute("username", username);

        return "userList"; // Переход к странице отображения пользователей
    }
}
