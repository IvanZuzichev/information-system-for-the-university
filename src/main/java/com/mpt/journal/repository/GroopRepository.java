package com.mpt.journal.repository;

import com.mpt.journal.model.GroopModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroopRepository extends JpaRepository<GroopModel, Long> {
    GroopModel findByName(String name);

    void deleteByDeletedIsTrue();

    List<GroopModel> findAllByName(String name);
}
