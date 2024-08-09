package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.entity.Waiter;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.web.dto.request.WaiterRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.WaiterResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public abstract class WaiterMapperDelegate implements WaiterMapper{

    @Autowired
    private WaiterMapper delegate;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Waiter upsertRequestToWaiter(WaiterRequest request) {
        Waiter waiter = delegate.upsertRequestToWaiter(request);
        waiter.setEvent(eventRepository.findById(request.getEventId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Event with ID {0} not found!", request.getEventId())
        )));

        return waiter;
    }

}
