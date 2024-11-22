package com.mpt.journal.controllerApi;


import com.mpt.journal.model.GroopModel;
import com.mpt.journal.repository.GroopRepository;
import com.mpt.journal.service.GroopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groops")
public class GroopControllerApi {

    @Autowired
    private GroopRepository groopService;

    @GetMapping
    public List<GroopModel> getAllGroops(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<GroopModel> groops = groopService.findAll();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, groops.size());

        return groops.subList(startIndex, endIndex);
    }

    @PostMapping
    public GroopModel addGroop(@RequestBody GroopModel newGroop) {
        return groopService.save(newGroop);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroopModel> updateGroop(@PathVariable Long id, @RequestBody GroopModel updatedGroop) {
        return groopService.findById(id)
                .map(existingGroop -> {
                    existingGroop.setName(updatedGroop.getName());
                    GroopModel savedGroop = groopService.save(existingGroop);
                    return ResponseEntity.ok(savedGroop);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroop(@PathVariable Long id) {
        return groopService.findById(id)
                .map(groop -> {
                    groopService.delete(groop);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllGroops() {
        groopService.deleteByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<GroopModel> searchGroops(@RequestParam(required = false) String name_groop,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {

        List<GroopModel> groops;

        if (name_groop != null && !name_groop.isEmpty()) {
            groops = groopService.findAllByName(name_groop);
        } else {
            groops = groopService.findAll();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, groops.size());
        return groops.subList(startIndex, endIndex);
    }
}