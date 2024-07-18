package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventMemberRepository;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public abstract class EventMemberMapperDelegate implements EventMemberMapper{

    @Autowired
    private EventMemberMapper delegate;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventMember upsertRequestToEventMember(UpsertEventMemberRequest request){
        EventMember eventMember = delegate.upsertRequestToEventMember(request);

        eventMember.setEvent(eventRepository.findById(request.getEventId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Event with ID {0} not found!", request.getEventId())
        )));

        return eventMember;
    }
}
