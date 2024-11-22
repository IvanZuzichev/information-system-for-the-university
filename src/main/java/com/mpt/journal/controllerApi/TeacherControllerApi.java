package com.mpt.journal.controllerApi;


import com.mpt.journal.model.DepartmentModel;
import com.mpt.journal.model.DisciplineModel;
import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.repository.DepartmentRepository;
import com.mpt.journal.repository.DisciplineRepository;
import com.mpt.journal.repository.TeacherReposotory;
import com.mpt.journal.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mpt.journal.model.UserModel;
import com.mpt.journal.repository.UserReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherControllerApi {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private TeacherReposotory teacherReposotory;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<TeacherModel> getAllTeachers() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherModel> getTeacherById(@PathVariable Long id) {
        return teacherReposotory.findById(id)
                .map(teacher -> ResponseEntity.ok().body(teacher))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TeacherModel createTeacher(@RequestBody TeacherModel newTeacher) {
        return teacherReposotory.save(newTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherModel> updateTeacher(@PathVariable Long id, @RequestBody TeacherModel teacherDetails) {
        return teacherReposotory.findById(id)
                .map(existingTeacher -> {
                    existingTeacher.setName(teacherDetails.getName());
                    existingTeacher.setSurname(teacherDetails.getSurname());
                    existingTeacher.setMidlename(teacherDetails.getMidlename());
                    existingTeacher.setDepartment(teacherDetails.getDepartment());
                    TeacherModel updatedTeacher = teacherService.updateTeacher(existingTeacher);
                    return ResponseEntity.ok().body(updatedTeacher);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeacher(@PathVariable long id) {
        return teacherReposotory.findById(id)
                .map(teacher -> {
                    teacherReposotory.delete(teacher);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{teacherId}/discipline/{disciplineId}")
    public ResponseEntity<Object> addDisciplineToTeacher(@PathVariable long teacherId, @PathVariable long disciplineId) {
        DisciplineModel disciplineModel = disciplineRepository.findById(disciplineId).orElse(null);
        TeacherModel teacherModel = teacherReposotory.findById(teacherId).orElse(null);

        if (teacherModel != null && disciplineModel != null) {
            teacherModel.getDisciplines().add(disciplineModel);
            teacherReposotory.save(teacherModel);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllTeachers() {
        teacherReposotory.deleteAllByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<TeacherModel> searchTeachers(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String surname) {
        if (name != null && !name.isEmpty()) {
            return teacherReposotory.findAllByName(name);
        } else if (surname != null && !surname.isEmpty()) {
            return teacherReposotory.findAllBySurname(surname);
        } else {
            return teacherReposotory.findAll();
        }
    }
}
