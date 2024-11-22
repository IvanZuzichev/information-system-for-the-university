package com.mpt.journal.service;

import com.mpt.journal.model.GroopModel;
import com.mpt.journal.repository.GroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryGroopServiceImpl implements GroopService {


    private final GroopRepository groopRepository;

    public InMemoryGroopServiceImpl(GroopRepository groopRepository) {
        this.groopRepository = groopRepository;
    }

    @Override
    public List<GroopModel> findAllGroops() {
        return groopRepository.findAll();
    }

    @Override
    public GroopModel findGroopById(long id) {
        return groopRepository.findById(id).orElse(null);
    }

    @Override
    public GroopModel addGroop(GroopModel groop) {
        return groopRepository.save(groop);
    }

    @Override
    public GroopModel updateGroop(GroopModel groop) {
        return groopRepository.save(groop);
    }

    @Override
    public void deleteGroop(long id) {
        GroopModel groop = groopRepository.findById(id).orElse(null);
        assert groop != null;
        groop.setDeleted(true);
        groopRepository.save(groop);
    }

    @Override
    public void deleteGroopDeletedIdTrue() {
        groopRepository.deleteByDeletedIsTrue();
    }

    @Override
    public void deleteAllGroops() {
        groopRepository.deleteAll();
    }

    @Override
    public List<GroopModel> findGroopsByName(String name) {
        return groopRepository.findAllByName(name);
    }
}