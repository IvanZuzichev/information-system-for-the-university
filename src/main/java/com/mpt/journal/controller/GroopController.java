package com.mpt.journal.controller;


import com.mpt.journal.model.GroopModel;
import com.mpt.journal.service.GroopService; // Предполагается, что у вас есть сервис для работы с группами
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
public class GroopController {

    @Autowired
    private GroopService groopService;

    @GetMapping("/groops")
    public String getAllGroops(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize) {

        List<GroopModel> groops = groopService.findAllGroops();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, groops.size());

        model.addAttribute("groops", groops.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) groops.size() / pageSize));

        return "groopList";
    }

    @PostMapping("/groops/add")
    public String addGroop(@RequestParam String name) {
        GroopModel Groop = new GroopModel(0, name, false, null);
        groopService.addGroop(Groop);
        return "redirect:/groops";
    }

    @PostMapping("/groops/update")
    public String updateGroop(@RequestParam int id,
                              @RequestParam String name_groop) {
        GroopModel groop = groopService.findGroopById(id);
        groop.setName(name_groop);
        groopService.updateGroop(groop);
        return "redirect:/groops";
    }

    @PostMapping("/groops/delete")
    public String deleteGroop(@RequestParam int id) {
        groopService.deleteGroop(id);
        return "redirect:/groops";
    }

    @PostMapping("/groops/delete-all")
    public String deleteAllGroops() {
        groopService.deleteGroopDeletedIdTrue();
        return "redirect:/groops";
    }

    @GetMapping("/groops/search")
    public String searchGroops(@RequestParam(required = false) String name_groop,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize,
                               Model model) {

        List<GroopModel> groops;

        if (name_groop != null && !name_groop.isEmpty()) {
            groops = groopService.findGroopsByName(name_groop);
        } else {
            groops = groopService.findAllGroops();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, groops.size());
        model.addAttribute("groops", groops.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) groops.size() / pageSize));

        // Фильтр
        model.addAttribute("name_groop", name_groop);

        return "groopList"; // Переход к странице отображения групп
    }
}