package com.mpt.journal.service;


import com.mpt.journal.model.TeacherModel;

import java.util.List;

public interface TeacherService {
    List<TeacherModel> findAllTeachers();

    TeacherModel findTeacherById(long id);

    TeacherModel addTeacher(TeacherModel teacher);

    TeacherModel updateTeacher(TeacherModel teacher);

    void deleteTeacher(long id);
    void deleteTeacherDeletedIsTrue();
    void deleteAllTeachers();

    TeacherModel getTeacherByName(String name);

    TeacherModel getTeacherBySurnname(String surname);
    List<TeacherModel> findTeachersByName(String name);

    List<TeacherModel> findTeachersBySurname(String surname);
}
