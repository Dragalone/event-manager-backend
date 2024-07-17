package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.service.AbstractEntityService;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;

import java.util.UUID;

public class EventServiceImpl extends AbstractEntityService<Event, UUID, EventRepository> implements EventService {
    public EventServiceImpl(EventRepository repository) {
        super(repository);
    }

    @Override
    protected Event updateFields(Event oldEntity, Event newEntity) {
        return null;
    }

    @Override
    public EventResponse createEvent(UpsertEventRequest request, UUID organizer) {
        return null;
    }
}
