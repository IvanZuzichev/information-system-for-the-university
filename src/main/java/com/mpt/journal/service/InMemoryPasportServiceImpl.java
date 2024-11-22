package com.mpt.journal.service;

import com.mpt.journal.model.PasportModel;
import com.mpt.journal.repository.PasportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryPasportServiceImpl implements PasportService {

    @Override
    public void deletePasportOdTrue() {
        pasportRepository.deleteByDeletedIsTrue();
    }

    @Autowired
    private PasportRepository pasportRepository;

    public InMemoryPasportServiceImpl(PasportRepository pasportRepository) {
        this.pasportRepository = pasportRepository;
    }

    @Override
    public List<PasportModel> findAllPasports() {
        return pasportRepository.findAll();
    }

    @Override
    public PasportModel findPasportById(long id) {
        return pasportRepository.findById(id).orElse(null);
    }

    @Override
    public PasportModel addPasport(PasportModel pasport) {
        return pasportRepository.save(pasport);
    }

    @Override
    public PasportModel updatePasport(PasportModel pasport) {
        return pasportRepository.save(pasport);
    }

    @Override
    public void deletePasport(long id) {
        PasportModel pasport = pasportRepository.findById(id).orElse(null);
        assert pasport != null;
        pasport.setDeleted(true);
        pasportRepository.save(pasport);
    }

    @Override
    public void deleteAllPasports() {
    pasportRepository.deleteAll();
    }

    @Override
    public List<PasportModel> findPasportsBySeries(String series) {
        return pasportRepository.findAllBySeries(series);
    }

    @Override
    public List<PasportModel> findPasportsByNumber(String number) {
        return pasportRepository.findAllByNumber(number);
    }
}
