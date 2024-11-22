package com.mpt.journal.controller;

import com.mpt.journal.model.PasportModel;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.StudentRepository;
import com.mpt.journal.service.PasportService; // Предполагается, что у вас есть сервис для работы с паспортами
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Transactional
public class PasportController {

    @Autowired
    private PasportService pasportService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/pasports")
    public String getAllPasports(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {

        List<PasportModel> pasports = pasportService.findAllPasports();
        List<StudentModel> students = studentRepository.findAll();
        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, pasports.size());

        model.addAttribute("pasports", pasports.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) pasports.size() / pageSize));
        model.addAttribute("students", students);
        return "pasportList";
    }

    @PostMapping("/pasports/add")
    public String addPasport(@RequestParam String series,
                             @RequestParam String number,
                             @RequestParam String studentname) {
        StudentModel student = studentRepository.findByName(studentname);
        PasportModel newPasport = new PasportModel(0, series, number, false, student);
        pasportService.addPasport(newPasport);
        return "redirect:/pasports";
    }

    @PostMapping("/pasports/update")
    public String updatePasport(@RequestParam int id,
                                @RequestParam String series,
                                @RequestParam String number,
                            @RequestParam String studentname) {
        StudentModel student = studentRepository.findByName(studentname);
        PasportModel updatedPasport = new PasportModel(id, series, number, false, student);
        pasportService.updatePasport(updatedPasport);
        return "redirect:/pasports";
    }

    @PostMapping("/pasports/delete")
    public String deletePasport(@RequestParam int id) {
        pasportService.deletePasport(id);
        return "redirect:/pasports";
    }

    @PostMapping("/pasports/delete-all")
    public String deleteAllPasports() {
        pasportService.deletePasportOdTrue();
        return "redirect:/pasports";
    }

    @GetMapping("/pasports/search")
    public String searchPasports(@RequestParam(required = false) String series,
                                 @RequestParam(required = false) String number,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 Model model) {

        List<PasportModel> pasports;

        if (series != null && !series.isEmpty()) {
            pasports = pasportService.findPasportsBySeries(series);
        } else if (number != null && !number.isEmpty()) {
            pasports = pasportService.findPasportsByNumber(number);
        } else {
            pasports = pasportService.findAllPasports();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, pasports.size());
        model.addAttribute("pasports", pasports.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) pasports.size() / pageSize));

        // Фильтр
        model.addAttribute("series", series);
        model.addAttribute("number", number);

        return "pasportList"; // Переход к странице отображения паспортов
    }
}
