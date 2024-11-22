package com.mpt.journal.controller;


import com.mpt.journal.model.DepartmentModel;
import com.mpt.journal.model.DisciplineModel;
import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.repository.DepartmentRepository;
import com.mpt.journal.repository.DisciplineRepository;
import com.mpt.journal.repository.TeacherReposotory;
import com.mpt.journal.service.TeacherService; // Предполагается, что у вас есть сервис для работы с учителями
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
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private TeacherReposotory teacherReposotory;

    @Autowired
    private DepartmentRepository departmentRepository;
    @GetMapping("/teachers")
    public String getAllTeachers(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {

        List<TeacherModel> teachers = teacherService.findAllTeachers();
        List<DisciplineModel> disciplines = disciplineRepository.findAll();
        List<DepartmentModel> departments = departmentRepository.findAll();
        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, teachers.size());

        model.addAttribute("teachers", teachers.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) teachers.size() / pageSize));
        model.addAttribute("disciplines", disciplines);
        model.addAttribute("departments", departments);
        return "teacherList";
    }

    @PostMapping("/teachers/add")
    public String addTeacher(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String midlename,
                             @RequestParam String department) {
        DepartmentModel departmentModel = departmentRepository.findByName(department);
        TeacherModel newTeacher = new TeacherModel(0, name, surname, midlename, false, null, departmentModel);
        teacherService.addTeacher(newTeacher);
        return "redirect:/teachers";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String midlename, @RequestParam String department) {
        DepartmentModel departmentModel = departmentRepository.findByName(department);
        TeacherModel updatedTeacher = teacherService.findTeacherById(id);
        updatedTeacher.setName(name);
        updatedTeacher.setSurname(surname);
        updatedTeacher.setMidlename(midlename);
        TeacherModel updatedTeachers = new TeacherModel(id, name, surname, midlename, false, null, departmentModel);
        teacherService.updateTeacher(updatedTeachers);
        return "redirect:/teachers";
    }

    @PostMapping("/teachers/discipline-add")
    public String addDiscipline(@RequestParam long id_discipline, @RequestParam long id_teacher) {
        DisciplineModel disciplineModel = disciplineRepository.findById(id_discipline).orElse(null);
        TeacherModel teacherModel = teacherReposotory.findById(id_teacher).orElse(null);
        teacherModel.getDisciplines().add(disciplineModel);
        return "redirect:/teachers";
    }

    @PostMapping("/teachers/delete")
    public String deleteTeacher(@RequestParam int id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }

    @PostMapping("/teachers/delete-all")
    public String deleteAllTeachers() {
        teacherService.deleteTeacherDeletedIsTrue();
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/search")
    public String searchTeachers(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String surname,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 Model model) {

        List<TeacherModel> teachers;

         if (name != null && !name.isEmpty()) {
            teachers = teacherService.findTeachersByName(name);
        } else if (surname != null && !surname.isEmpty()) {
            teachers = teacherService.findTeachersBySurname(surname);
        } else {
            teachers = teacherService.findAllTeachers();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, teachers.size());
        model.addAttribute("teachers", teachers.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) teachers.size() / pageSize));

        // Фильтр
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);

        return "teacherList";
    }
}