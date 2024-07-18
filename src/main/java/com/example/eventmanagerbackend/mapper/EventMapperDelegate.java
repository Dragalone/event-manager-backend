package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.UserRepository;
import com.example.eventmanagerbackend.service.UserService;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public abstract class EventMapperDelegate implements EventMapper {

    @Autowired
    private EventMapper delegate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public EventResponse eventToResponse(Event event){
        EventResponse response = delegate.eventToResponse(event);
        response.setOrganizer(userMapper.userToResponse(event.getOrganizer()));
        return response;
    }

    @Override
    public Event upsertRequestToEvent(UpsertEventRequest request){
        Event event = delegate.upsertRequestToEvent(request);
        event.setOrganizer(userRepository.findById(request.getOrganizerId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("User with ID {0} not found!", request.getOrganizerId())
        )));
        return event;
    }

}
