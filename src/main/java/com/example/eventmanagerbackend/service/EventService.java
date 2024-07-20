package com.example.eventmanagerbackend.service;


import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EventService extends EntityService<EventResponse,UpsertEventRequest, UUID> {
    List<EventResponse> findAllByOrganizerId(UUID orgId, Pageable pageable);

    List<EventResponse> filterBy(EventFilterRequest filter);
}
