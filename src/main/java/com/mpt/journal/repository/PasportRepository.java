package com.mpt.journal.repository;

import com.mpt.journal.model.PasportModel;
import com.mpt.journal.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasportRepository extends JpaRepository<PasportModel, Long> {
    PasportModel findBySeries(String series);
    PasportModel findByNumber(String serialNumber);
    void deleteByDeletedIsTrue();

    List<PasportModel> findAllBySeries(String series);

    List<PasportModel> findAllByNumber(String number);
}
