package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.model.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeacherReposotory extends JpaRepository<TeacherModel, Long> {
    TeacherModel findByName(String name);
    TeacherModel findBySurname(String surName);
    void deleteAllByDeletedIsTrue();

    List<TeacherModel> findAllByName(String name);

    List<TeacherModel> findAllBySurname(String surname);
}
