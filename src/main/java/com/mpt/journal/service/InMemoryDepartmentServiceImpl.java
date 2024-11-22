package com.mpt.journal.service;

import com.mpt.journal.model.DepartmentModel;
import com.mpt.journal.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryDepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    public InMemoryDepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentModel> findAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public DepartmentModel findDepartmentById(long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public DepartmentModel addDepartment(DepartmentModel department) {
        return departmentRepository.save(department);
    }

    @Override
    public DepartmentModel updateDepartment(DepartmentModel department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(long id) {
        DepartmentModel department = departmentRepository.findById(id).orElse(null);
        assert department != null;
        department.setDeleted(true);
        departmentRepository.save(department);
    }

    @Override
    public void deleteDepartmentDeletedIdTrue() {
        departmentRepository.deleteByDeletedIsTrue();
    }

    @Override
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    @Override
    public List<DepartmentModel> findDepartmentByName(String name) {
        return departmentRepository.findAllByName(name);
    }
}
