package com.mpt.journal.repository;

import com.mpt.journal.model.DepartmentModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {
    DepartmentModel findByName(String name);

    void deleteByDeletedIsTrue();

    List<DepartmentModel> findAllByName(String name);
}
