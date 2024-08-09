package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.web.dto.request.WaiterRequest;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import com.example.eventmanagerbackend.web.dto.response.WaiterResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WaiterService extends EntityService<WaiterResponse, WaiterRequest, UUID>{

    ModelListResponse<WaiterResponse> findWaitersByEventId(UUID id, Pageable pageable);
}
