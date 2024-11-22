package com.mpt.journal.controllerApi;


import com.mpt.journal.model.GroopModel;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.GroopRepository;
import com.mpt.journal.repository.StudentRepository;
import com.mpt.journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentControllerApi {

    @Autowired
    private StudentRepository studentService;

    @Autowired
    private GroopRepository groopRepository;

    @GetMapping
    public List<StudentModel> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String mail) {

        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable Long id) {
        Optional<StudentModel> student = studentService.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StudentModel createStudent(@RequestBody StudentModel newStudent) {
        GroopModel groopModel = groopRepository.findByName(newStudent.getGroopModel().getName());
        newStudent.setGroopModel(groopModel); // Установка группы
        return studentService.save(newStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentModel> updateStudent(@PathVariable Long id, @RequestBody StudentModel studentDetails) {
        return studentService.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(studentDetails.getName());
                    existingStudent.setLastName(studentDetails.getLastName());
                    existingStudent.setFirstName(studentDetails.getFirstName());
                    existingStudent.setMiddleName(studentDetails.getMiddleName());
                    existingStudent.setMail(studentDetails.getMail());
                    existingStudent.setGroopModel(groopRepository.findByName(studentDetails.getGroopModel().getName()));
                    StudentModel updatedStudent = studentService.save(existingStudent);
                    return ResponseEntity.ok(updatedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        return studentService.findById(id)
                .map(student -> {
                    studentService.delete(student);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllDeletedStudents() {
        studentService.deleteAllByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<StudentModel> searchStudents(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String mail,
                                             @RequestParam(required = false) String middleName) {
        // Можно добавить логику поиска
        return studentService.findAllByName(name);
    }
}
