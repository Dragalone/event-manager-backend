package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;

import java.util.UUID;

public interface EventService extends EntityService<Event, UUID> {

    EventResponse createEvent(UpsertEventRequest request, UUID organizer);



}
