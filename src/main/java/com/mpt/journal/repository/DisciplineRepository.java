package com.mpt.journal.repository;

import com.mpt.journal.model.DisciplineModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<DisciplineModel, Long> {
    DisciplineModel findByName(String name);

    void deleteByDeletedIsTrue();

    List<DisciplineModel> findAllByName(String name);


}
