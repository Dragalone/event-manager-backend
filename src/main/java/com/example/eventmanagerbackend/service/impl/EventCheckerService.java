package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.aop.AccessCheckType;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.service.AbstractAccessCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class EventCheckerService extends AbstractAccessCheckerService {

    private final EventRepository eventRepository;

    @Override
    protected boolean check(UUID entityId, UUID userId) {
        return eventRepository.existsByIdAndOrganizerId(entityId,userId);
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.EVENT;
    }
}
