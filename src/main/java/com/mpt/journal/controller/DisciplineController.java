package com.mpt.journal.controller;


import com.mpt.journal.model.DisciplineModel;
import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.repository.DisciplineRepository;
import com.mpt.journal.repository.TeacherReposotory;
import com.mpt.journal.service.DisciplineService;
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
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private TeacherReposotory teacherReposotory;
    @Autowired
    private DisciplineRepository disciplineRepository;

    @GetMapping("/disciplines")
    public String getAllDisciplines(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int pageSize) {

        List<DisciplineModel> disciplines = disciplineService.findAllDisciplines();
        List<TeacherModel> teachers = teacherReposotory.findAll();
        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, disciplines.size());

        model.addAttribute("disciplines", disciplines.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) disciplines.size() / pageSize));
        model.addAttribute("teachers", teachers);
        return "disciplineList"; // Переход к странице отображения дисциплин
    }

    @PostMapping("/disciplines/add")
    public String addDiscipline(@RequestParam String name_discipline) {
        DisciplineModel newDiscipline = new DisciplineModel(0, name_discipline, false, null);
        disciplineService.addDiscipline(newDiscipline);
        return "redirect:/disciplines";
    }

    @PostMapping("/disciplines/update")
    public String updateDiscipline(@RequestParam int id,
                                   @RequestParam String name_discipline) {
        DisciplineModel discipline = disciplineService.findDisciplineById(id);
        discipline.setName(name_discipline);
        disciplineService.updateDiscipline(discipline);
        return "redirect:/disciplines";
    }
    @PostMapping("/disciplines/teachers-add")
    public String addTeacher(@RequestParam long id_teacher, @RequestParam  long id_discipline) {
        TeacherModel teachers = teacherReposotory.findById(id_teacher).orElse(null);
        DisciplineModel disciplines = disciplineRepository.findById(id_discipline).orElse(null);
        disciplines.getTeachers().add(teachers);
        return "redirect:/disciplines";
    }

    @PostMapping("/disciplines/delete")
    public String deleteDiscipline(@RequestParam int id) {
        disciplineService.deleteDiscipline(id);
        return "redirect:/disciplines";
    }

    @PostMapping("/disciplines/delete-all")
    public String deleteAllDisciplines() {
        disciplineService.deleteDisciplineIsTrue();
        return "redirect:/disciplines";
    }

    @GetMapping("/disciplines/search")
    public String searchDisciplines(@RequestParam(required = false) String name_discipline,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    Model model) {

        List<DisciplineModel> disciplines;

        if (name_discipline != null && !name_discipline.isEmpty()) {
            disciplines = disciplineService.findAllDisciplines();
        } else {
            disciplines = disciplineService.findAllDisciplines();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, disciplines.size());
        model.addAttribute("disciplines", disciplines.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) disciplines.size() / pageSize));

        // Фильтр
        model.addAttribute("name_discipline", name_discipline);

        return "disciplineList";
    }
}