package com.mpt.journal.controller;


import com.mpt.journal.model.EventModel;
import com.mpt.journal.service.EventService; // Импортируем новый сервис для работы с событиями
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
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public String getAllEvents(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize) {

        List<EventModel> events = eventService.findAllEvents();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, events.size());

        model.addAttribute("events", events.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) events.size() / pageSize));

        return "eventList"; // Переход к странице отображения событий
    }

    @PostMapping("/events/add")
    public String addEvent(@RequestParam String name,
                           @RequestParam String time_date) {
        EventModel event = new EventModel(0, name, time_date, false);
        eventService.addEvent(event);
        return "redirect:/events";
    }

    @PostMapping("/events/update")
    public String updateEvent(@RequestParam int id,
                              @RequestParam String name,
                              @RequestParam String time_date) {
        EventModel updatedEvent = new EventModel(id, name, time_date, false);
        eventService.updateEvent(updatedEvent);
        return "redirect:/events";
    }

    @PostMapping("/events/delete")
    public String deleteEvent(@RequestParam int id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    @PostMapping("/events/delete-all")
    public String deleteAllEvents() {
        eventService.deleteEventDeletedIdTrue();
        return "redirect:/events";
    }

    @GetMapping("/events/search")
    public String searchEvents(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize,
                               Model model) {

        List<EventModel> events;

        if (name != null && !name.isEmpty()) {
            events = eventService.findEventsByName(name);
        } else {
            events = eventService.findAllEvents();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, events.size());
        model.addAttribute("events", events.subList(startIndex, endIndex));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) events.size() / pageSize));

        // Фильтр
        model.addAttribute("name", name);

        return "eventList"; // Переход к странице отображения событий
    }
}
