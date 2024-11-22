package com.mpt.journal.service;

import com.mpt.journal.model.PasportModel;

import java.util.List;

public interface PasportService {
    List<PasportModel> findAllPasports();

    PasportModel findPasportById(long id);

    PasportModel addPasport(PasportModel pasport);

    PasportModel updatePasport(PasportModel pasport);

    void deletePasport(long id);
    void deletePasportOdTrue();
    void deleteAllPasports();

    List<PasportModel> findPasportsBySeries(String series);
    List<PasportModel> findPasportsByNumber(String number);

}
