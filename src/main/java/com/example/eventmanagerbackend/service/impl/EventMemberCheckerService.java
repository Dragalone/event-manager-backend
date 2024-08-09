package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.aop.AccessCheckType;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventMemberRepository;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.service.AbstractAccessCheckerService;
import com.example.eventmanagerbackend.service.EventMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class EventMemberCheckerService extends AbstractAccessCheckerService {

    private final EventMemberRepository eventMemberRepository;

    private final EventRepository eventRepository;

    @Override
    protected boolean check(UUID entityId, UUID userId) {
        EventMember eventMember = eventMemberRepository.findById(entityId).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Event member with ID {0} not found!", entityId)
        ));
        return eventRepository.existsByIdAndOrganizerId(eventMember.getEvent().getId(), userId);
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.EVENT_MEMBER;
    }
}
