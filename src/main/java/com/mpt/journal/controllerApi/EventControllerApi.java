package com.mpt.journal.controllerApi;


import com.mpt.journal.controller.EventController;
import com.mpt.journal.model.EventModel;
import com.mpt.journal.repository.EventRepository;
import com.mpt.journal.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventControllerApi {

    @Autowired
    private EventRepository eventService;

    @GetMapping
    public List<EventModel> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<EventModel> events = eventService.findAll();

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, events.size());

        return events.subList(startIndex, endIndex);
    }

    @PostMapping
    public EventModel addEvent(@RequestBody EventModel newEvent) {
        return eventService.save(newEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventModel> updateEvent(@PathVariable Long id, @RequestBody EventModel updatedEvent) {
        return eventService.findById(id)
                .map(existingEvent -> {
                    existingEvent.setName(updatedEvent.getName());
                    existingEvent.setTime_date(updatedEvent.getTime_date());
                    EventModel savedEvent = eventService.save(existingEvent);
                    return ResponseEntity.ok(savedEvent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        return eventService.findById(id)
                .map(event -> {
                    eventService.delete(event);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllEvents() {
        eventService.deleteByDeletedIsTrue();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<EventModel> searchEvents(@RequestParam(required = false) String name,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {

        List<EventModel> events;

        if (name != null && !name.isEmpty()) {
            events = eventService.findAllByName(name);
        } else {
            events = eventService.findAll();
        }

        // Пагинация
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, events.size());
        return events.subList(startIndex, endIndex);
    }
}
