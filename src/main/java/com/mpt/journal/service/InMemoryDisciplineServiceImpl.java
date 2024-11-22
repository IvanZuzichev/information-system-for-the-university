package com.mpt.journal.service;

import com.mpt.journal.model.DisciplineModel;
import com.mpt.journal.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class InMemoryDisciplineServiceImpl implements DisciplineService {

        private final DisciplineRepository disciplineRepository;

       public InMemoryDisciplineServiceImpl(DisciplineRepository disciplineRepository) {
           this.disciplineRepository = disciplineRepository;
       }

        @Override
        public List<DisciplineModel> findAllDisciplines() {
            return disciplineRepository.findAll();
        }

        @Override
        public DisciplineModel findDisciplineById(long id) {
            return disciplineRepository.findById(id).orElse(null);
        }

        @Override
        public DisciplineModel addDiscipline(DisciplineModel discipline) {
            return disciplineRepository.save(discipline);
        }

        @Override
        public DisciplineModel updateDiscipline(DisciplineModel discipline) {
            return disciplineRepository.save(discipline);
        }

        @Override
        public void deleteDiscipline(long id) {
            DisciplineModel discipline = findDisciplineById(id);
            assert discipline != null;
            discipline.setDeleted(true);
            disciplineRepository.save(discipline);
        }

        @Override
        public void deleteAllDisciplines() {
            disciplineRepository.deleteAll();
        }

        @Override
        public List<DisciplineModel> findAllByDiscipline(String discipline) {
            return disciplineRepository.findAllByName(discipline);
        }

        @Override
        public void deleteDisciplineIsTrue() {
            disciplineRepository.deleteByDeletedIsTrue();
        }
    }
