package com.mpt.journal.service;

import com.mpt.journal.model.DisciplineModel;

import java.util.List;

public interface DisciplineService {

    List<DisciplineModel> findAllDisciplines();

    DisciplineModel findDisciplineById(long id);

    DisciplineModel addDiscipline(DisciplineModel discipline);

    DisciplineModel updateDiscipline(DisciplineModel discipline);

    void deleteDiscipline(long id);
    void deleteDisciplineIsTrue();
    void deleteAllDisciplines();

    List<DisciplineModel> findAllByDiscipline(String discipline);

}