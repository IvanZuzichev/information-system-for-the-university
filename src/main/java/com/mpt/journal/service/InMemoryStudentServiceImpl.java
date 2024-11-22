package com.mpt.journal.service;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryStudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public InMemoryStudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentModel> findAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public StudentModel findStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public StudentModel addStudent(StudentModel student) {
        return studentRepository.save(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        StudentModel student = studentRepository.findById(id).orElse(null);
        assert student != null;
        student.setDeleted(true);
        studentRepository.save(student);
    }

    @Override
    public void deleteAllStudent() {
        studentRepository.deleteAll();
    }

    @Override
    public StudentModel findStudentsByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<StudentModel> findStudentsByMiddleName(String middleName) {
        return List.of();
    }

    @Override
    public List<StudentModel> findStudentsByMail(String mail) {
        return studentRepository.findAllByMail(mail);
    }

    @Override
    public void deleteStudentDeletedIsTrue() {
        studentRepository.deleteAllByDeletedIsTrue();
    }

    @Override
    public List<StudentModel> findAllByName(String name) {
        return studentRepository.findAllByName(name);
    }

    @Override
    public List<StudentModel> findAllByLastName(String lastName) {
        return studentRepository.findAllByLastName(lastName);
    }
}
