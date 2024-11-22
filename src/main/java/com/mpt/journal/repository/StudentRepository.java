package com.mpt.journal.repository;

import com.mpt.journal.controller.StudentController;
import com.mpt.journal.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    StudentModel findByName(String name);

    void deleteAllByDeletedIsTrue();

    List<StudentModel> findAllByMail(String mail);
    List<StudentModel> findAllByMiddleName(String middleName);
    List<StudentModel> findAllByName(String name);
    List<StudentModel> findAllByLastName(String lastName);
}
