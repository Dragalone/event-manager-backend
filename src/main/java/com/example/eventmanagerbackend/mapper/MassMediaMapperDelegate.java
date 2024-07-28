package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.MassMedia;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.repository.MassMediaRepository;
import com.example.eventmanagerbackend.web.dto.request.UpsertMassMediaRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public abstract class MassMediaMapperDelegate implements MassMediaMapper {

    @Autowired
    private MassMediaMapper delegate;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MassMediaRepository massMediaRepository;

    @Override
    public MassMedia upsertRequestToMassMedia(UpsertMassMediaRequest request)
    {
        MassMedia massMedia = delegate.upsertRequestToMassMedia(request);
        massMedia.setEvent(eventRepository.findById(request.getEventId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Event with ID {0} not found!", request.getEventId())
        )));

        return massMedia;
    }


}
