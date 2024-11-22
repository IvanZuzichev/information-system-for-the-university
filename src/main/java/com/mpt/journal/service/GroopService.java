package com.mpt.journal.service;

import com.mpt.journal.model.GroopModel;

import java.util.List;

public interface GroopService {
    List<GroopModel> findAllGroops();

    GroopModel findGroopById(long id);

    GroopModel addGroop(GroopModel groop);

    GroopModel updateGroop(GroopModel groop);

    void deleteGroop(long id);
    void deleteGroopDeletedIdTrue();
    void deleteAllGroops();

    List<GroopModel> findGroopsByName(String name);

}
