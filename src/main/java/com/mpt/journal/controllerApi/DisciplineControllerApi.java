package com.mpt.journal.controllerApi;


import com.mpt.journal.model.DisciplineModel;
import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.repository.DisciplineRepository;
import com.mpt.journal.repository.TeacherReposotory;
import com.mpt.journal.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineControllerApi {

    @Autowired
    private DisciplineRepository disciplineService;

    @Autowired
    private TeacherReposotory teacherReposotory;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @GetMapping
    public List<DisciplineModel> getAllDisciplines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<DisciplineModel> disciplines = disciplineService.findAll();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, disciplines.size());

        return disciplines.subList(startIndex, endIndex);
    }

    @PostMapping
    public DisciplineModel addDiscipline(@RequestBody DisciplineModel newDiscipline) {
        return disciplineService.save(newDiscipline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineModel> updateDiscipline(@PathVariable Long id, @RequestBody DisciplineModel updatedDiscipline) {
        return disciplineService.findById(id)
                .map(existingDiscipline -> {
                    existingDiscipline.setName(updatedDiscipline.getName());
                    DisciplineModel savedDiscipline = disciplineService.save(existingDiscipline);
                    return ResponseEntity.ok(savedDiscipline);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/teachers")
    public ResponseEntity<Void> addTeacherToDiscipline(@PathVariable long id, @RequestParam long id_teacher) {
        TeacherModel teacher = teacherReposotory.findById(id_teacher).orElse(null);
        DisciplineModel discipline = disciplineRepository.findById(id).orElse(null);

        if (teacher != null && discipline != null) {
            discipline.getTeachers().add(teacher);
            disciplineService.save(discipline);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDiscipline(@PathVariable Long id) {
        return disciplineService.findById(id)
                .map(discipline -> {
                    disciplineService.delete(discipline);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllDisciplines() {
        disciplineService.deleteByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<DisciplineModel> searchDisciplines(@RequestParam(required = false) String name_discipline,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int pageSize) {

        List<DisciplineModel> disciplines;

        if (name_discipline != null && !name_discipline.isEmpty()) {
            disciplines = disciplineService.findAllByName(name_discipline);
        } else {
            disciplines = disciplineService.findAll();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, disciplines.size());
        return disciplines.subList(startIndex, endIndex);
    }
}
