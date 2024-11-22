package com.mpt.journal.service;

import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.repository.TeacherReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryTeacherServicelmpl implements TeacherService {


    private final TeacherReposotory teacherRepository;

    public InMemoryTeacherServicelmpl(TeacherReposotory teacherService) {
        this.teacherRepository = teacherService;
    }

    @Override
    public List<TeacherModel> findAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public TeacherModel findTeacherById(long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public TeacherModel addTeacher(TeacherModel teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public TeacherModel updateTeacher(TeacherModel teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacherDeletedIsTrue() {
        teacherRepository.deleteAllByDeletedIsTrue();
    }

    @Override
    public void deleteTeacher(long id) {
        TeacherModel teacher = teacherRepository.findById(id).orElse(null);
        assert teacher != null;
        teacher.setDeleted(true);
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteAllTeachers() {
    teacherRepository.deleteAll();
    }

    @Override
    public TeacherModel getTeacherByName(String name) {
        return teacherRepository.findByName(name);
    }

    @Override
    public TeacherModel getTeacherBySurnname(String surname) {
        return teacherRepository.findBySurname(surname);
    }

    @Override
    public List<TeacherModel> findTeachersByName(String name) {
        return teacherRepository.findAllByName(name);
    }

    @Override
    public List<TeacherModel> findTeachersBySurname(String surname) {
        return teacherRepository.findAllBySurname(surname);
    }
}
