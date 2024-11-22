package com.mpt.journal.controller;

import com.mpt.journal.model.DepartmentModel;

import com.mpt.journal.service.DepartmentService; // Предполагается, что у вас есть сервис для работы с группами

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
public class DepartmentConstroller {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public String getAllDepartments(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize) {

        List<DepartmentModel> departments = departmentService.findAllDepartments();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, departments.size());

        model.addAttribute("departments", departments.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) departments.size() / pageSize));

        return "departmentList"; // Переход к странице отображения групп
    }

    @PostMapping("/departments/add")
    public String addDepartment(@RequestParam String name) {
        DepartmentModel Department = new DepartmentModel(0, name, false, null);
        departmentService.addDepartment(Department);
        return "redirect:/departments";
    }

    @PostMapping("/departments/update")
    public String updateDepartment(@RequestParam int id,
                              @RequestParam String name_groop) {
        DepartmentModel updatedDepartment = new DepartmentModel(id, name_groop, false, null);
        departmentService.updateDepartment(updatedDepartment);
        return "redirect:/departments";
    }

    @PostMapping("/departments/delete")
    public String deleteDepartment(@RequestParam int id) {
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }

    @PostMapping("/departments/delete-all")
    public String deleteAllDepartments() {
        departmentService.deleteDepartmentDeletedIdTrue();
        return "redirect:/departments";
    }

    @GetMapping("/departments/search")
    public String searchDepartments(@RequestParam(required = false) String name_departments,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize,
                               Model model) {

        List<DepartmentModel> departments;

        if (name_departments != null && !name_departments.isEmpty()) {
            departments = departmentService.findDepartmentByName(name_departments);
        } else {
            departments = departmentService.findAllDepartments();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, departments.size());
        model.addAttribute("departments", departments.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) departments.size() / pageSize));

        // Фильтр
        model.addAttribute("name_department", name_departments);

        return "departmentList"; // Переход к странице отображения групп
    }
}