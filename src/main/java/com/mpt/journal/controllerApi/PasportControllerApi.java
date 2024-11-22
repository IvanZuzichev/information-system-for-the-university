package com.mpt.journal.controllerApi;


import com.mpt.journal.model.PasportModel;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.PasportRepository;
import com.mpt.journal.repository.StudentRepository;
import com.mpt.journal.service.PasportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pasports")
public class PasportControllerApi {

    @Autowired
    private PasportRepository pasportService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<PasportModel> getAllPasports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<PasportModel> pasports = pasportService.findAll();
        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, pasports.size());

        return pasports.subList(startIndex, endIndex);
    }

    @PostMapping
    public PasportModel addPasport(@RequestBody PasportModel newPasport) {
        StudentModel student = studentRepository.findByName(newPasport.getStudent().getName());
        newPasport.setStudent(student); // Установка студента
        return pasportService.save(newPasport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasportModel> updatePasport(@PathVariable Long id, @RequestBody PasportModel updatedPasport) {
        return pasportService.findById(id)
                .map(existingPasport -> {
                    existingPasport.setSeries(updatedPasport.getSeries());
                    existingPasport.setNumber(updatedPasport.getNumber());
                    existingPasport.setStudent(studentRepository.findByName(updatedPasport.getStudent().getName()));
                    PasportModel savedPasport = pasportService.save(existingPasport);
                    return ResponseEntity.ok(savedPasport);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePasport(@PathVariable Long id) {
        return pasportService.findById(id)
                .map(pasport -> {
                    pasportService.delete(pasport);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllPasports() {
        pasportService.deleteByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<PasportModel> searchPasports(@RequestParam(required = false) String series,
                                             @RequestParam(required = false) String number,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int pageSize) {

        List<PasportModel> pasports;

        if (series != null && !series.isEmpty()) {
            pasports = pasportService.findAllBySeries(series);
        } else if (number != null && !number.isEmpty()) {
            pasports = pasportService.findAllByNumber(number);
        } else {
            pasports = pasportService.findAll();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, pasports.size());
        return pasports.subList(startIndex, endIndex);
    }
}