package com.mpt.journal.service;


import com.mpt.journal.model.EventModel;
import com.mpt.journal.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryEventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public InMemoryEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventModel> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public EventModel findEventById(long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public EventModel addEvent(EventModel event) {
        return eventRepository.save(event);
    }

    @Override
    public EventModel updateEvent(EventModel event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(long id) {
        EventModel event = eventRepository.findById(id).orElse(null);
        assert event != null;
        event.setDeleted(true);
        eventRepository.save(event);
    }

    @Override
    public void deleteEventDeletedIdTrue() {
        eventRepository.deleteByDeletedIsTrue();
    }

    @Override
    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    @Override
    public List<EventModel> findEventsByName(String name) {
        return eventRepository.findAllByName(name);
    }
}
