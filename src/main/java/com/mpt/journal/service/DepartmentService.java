package com.mpt.journal.service;

import com.mpt.journal.model.DepartmentModel;


import java.util.List;

public interface DepartmentService {
    List<DepartmentModel> findAllDepartments();

    DepartmentModel findDepartmentById(long id);

    DepartmentModel addDepartment(DepartmentModel department);

    DepartmentModel updateDepartment(DepartmentModel department);

    void deleteDepartment(long id);
    void deleteDepartmentDeletedIdTrue();
    void deleteAllDepartments();

    List<DepartmentModel> findDepartmentByName(String name);

}
