package com.mpt.journal.controller;

import com.mpt.journal.model.GroopModel;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.GroopRepository;
import com.mpt.journal.repository.StudentRepository;
import com.mpt.journal.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@Transactional
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroopRepository groopRepository;
    @GetMapping("/students")
    public String getAllStudents(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String middleName,
                                 @RequestParam(required = false) String mail) {

        List<StudentModel> students;

            students = studentService.findAllStudent();

            List<GroopModel> groops;
            groops = groopRepository.findAll();

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, students.size());

        model.addAttribute("students", students.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) students.size() / pageSize));
        model.addAttribute("name", name);
        model.addAttribute("middleName", middleName);
        model.addAttribute("mail", mail);
        model.addAttribute("groops", groops);
        return "studentList";
    }


    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName,
                             @RequestParam String mail,
                             @RequestParam String groop) {
        GroopModel groopModel = groopRepository.findByName(groop);

        StudentModel newStudent = new StudentModel(0, name, lastName, firstName, middleName, mail, false, null, groopModel);
        studentService.addStudent(newStudent);
        return "redirect:/students";
    }

    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName,
                                @RequestParam String mail,
                                @RequestParam String groop) {
        GroopModel groopModel = groopRepository.findByName(groop);

        StudentModel updatedStudent = new StudentModel(id, name, lastName, firstName, middleName, mail, false, null, groopModel);
        studentService.updateStudent(updatedStudent);
        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
    @PostMapping("/students/delete-all")
    public String deleteAllDeletedStudents() {
        studentService.deleteStudentDeletedIsTrue();
        return "redirect:/students";
    }


    @GetMapping("/students/search")
    public String searchStudents(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String mail,
                                 @RequestParam(value = "middleName", required = false) String middleName,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 Model model) {

        List<StudentModel> students;

        if (mail != null && !mail.isEmpty()) {
            students = studentService.findStudentsByMail(mail);
        } else if (middleName != null && !middleName.isEmpty()) {
            students = studentService.findStudentsByMiddleName(middleName);
        } else {
            students = studentService.findAllStudent();
        }


        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, students.size());
        model.addAttribute("students", students.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) students.size() / pageSize));

        model.addAttribute("name", name);
        model.addAttribute("mail", mail);
        model.addAttribute("middleName", middleName);
        model.addAttribute("filter", name + " " + mail + " " + middleName);

        return "studentList";
    }



}
