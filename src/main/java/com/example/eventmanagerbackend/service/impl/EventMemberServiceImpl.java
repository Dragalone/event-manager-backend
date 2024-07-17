package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.repository.EventMemberRepository;
import com.example.eventmanagerbackend.service.AbstractEntityService;
import com.example.eventmanagerbackend.service.EventMemberService;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;

import java.util.UUID;

public class EventMemberServiceImpl extends AbstractEntityService<EventMember, UUID, EventMemberRepository> implements EventMemberService {
    public EventMemberServiceImpl(EventMemberRepository repository) {
        super(repository);
    }

    @Override
    protected EventMember updateFields(EventMember oldEntity, EventMember newEntity) {
        return null;
    }

    @Override
    public EventMemberResponse createEventMember(UpsertEventRequest request, UUID event) {
        return null;
    }
}
