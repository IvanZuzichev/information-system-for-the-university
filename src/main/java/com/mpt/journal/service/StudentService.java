package com.mpt.journal.service;

import com.mpt.journal.model.StudentModel;

import java.util.List;

public interface StudentService {
    List<StudentModel> findAllStudent();

    StudentModel findStudentById(long id);

    StudentModel addStudent(StudentModel student);

    StudentModel updateStudent(StudentModel student);

    void deleteStudent(long id);
    void deleteStudentDeletedIsTrue();
    void deleteAllStudent();

    StudentModel findStudentsByName(String name);

    List<StudentModel> findStudentsByMiddleName(String middleName);

    List <StudentModel> findStudentsByMail(String mail);

   List<StudentModel> findAllByName(String name);
   List<StudentModel> findAllByLastName(String lastName);

}
