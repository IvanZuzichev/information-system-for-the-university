package com.mpt.journal.service;

import com.mpt.journal.model.EventModel;

import java.util.List;

public interface EventService {
    List<EventModel> findAllEvents();

    EventModel findEventById(long id);

    EventModel addEvent(EventModel event);

    EventModel updateEvent(EventModel event);

    void deleteEvent(long id);

    void deleteEventDeletedIdTrue();

    void deleteAllEvents();

    List<EventModel> findEventsByName(String name);
}
