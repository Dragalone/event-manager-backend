package com.example.eventmanagerbackend.service;


import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface EventService extends EntityService<EventResponse,UpsertEventRequest, UUID> {
    ModelListResponse<EventResponse> findAllByOrganizerId(UUID orgId, Pageable pageable);

    ModelListResponse<EventResponse> filterBy(EventFilterRequest filter);

    ResponseEntity<String> check(UUID id);
}
