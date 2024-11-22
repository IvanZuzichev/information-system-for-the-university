package com.mpt.journal.controllerApi;


import com.mpt.journal.controller.DepartmentConstroller;
import com.mpt.journal.model.DepartmentModel;
import com.mpt.journal.repository.DepartmentRepository;
import com.mpt.journal.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentControllerApi {

    @Autowired
    private DepartmentRepository departmentService;

    @GetMapping
    public List<DepartmentModel> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<DepartmentModel> departments = departmentService.findAll();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, departments.size());

        return departments.subList(startIndex, endIndex);
    }

    @PostMapping
    public DepartmentModel addDepartment(@RequestBody DepartmentModel newDepartment) {
        return departmentService.save(newDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentModel> updateDepartment(@PathVariable Long id, @RequestBody DepartmentModel updatedDepartment) {
        return departmentService.findById(id)
                .map(existingDepartment -> {
                    existingDepartment.setName(updatedDepartment.getName());
                    DepartmentModel savedDepartment = departmentService.save(existingDepartment);
                    return ResponseEntity.ok(savedDepartment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        return departmentService.findById(id)
                .map(department -> {
                    departmentService.delete(department);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllDepartments() {
        departmentService.deleteByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<DepartmentModel> searchDepartments(@RequestParam(required = false) String name_departments,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int pageSize) {

        List<DepartmentModel> departments;

        if (name_departments != null && !name_departments.isEmpty()) {
            departments = departmentService.findAllByName(name_departments);
        } else {
            departments = departmentService.findAll();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, departments.size());
        return departments.subList(startIndex, endIndex);
    }
}
