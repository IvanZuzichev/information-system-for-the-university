package com.mpt.journal.repository;

import com.mpt.journal.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventModel,Long> {
    EventModel findByName(String name);

    void deleteByDeletedIsTrue(); // Предполагается, что у вас есть поле deleted в EventModel

    List<EventModel> findAllByName(String name);
}
