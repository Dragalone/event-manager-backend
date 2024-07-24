package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.exception.AlreadyExistsException;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.mapper.EventMapper;
import com.example.eventmanagerbackend.repository.EventRepository;

import com.example.eventmanagerbackend.repository.EventSpecification;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;

    private final EventRepository repository;

    @Override
    public ModelListResponse<EventResponse>findAll(Pageable pageable) {
        log.info("Find all events");
        Page<Event> events = repository.findAll(pageable);
        return ModelListResponse.<EventResponse>builder()
                .totalCount(events.getTotalElements())
                .data(events.stream().map(eventMapper::eventToResponse).toList())
                .build();

    }

    @Override
    public EventResponse findById(UUID id) {
        log.info("Find event with ID: {}", id);
        return eventMapper.eventToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Event with ID {0} not found!", id)
                )));
    }

    @Override
    public EventResponse create(UpsertEventRequest entityRequest) {
        log.info("Create event {}",entityRequest);
        return eventMapper.eventToResponse(
                repository.save(eventMapper.upsertRequestToEvent(entityRequest))
        );
    }

    @Override
    public EventResponse update(UUID id, UpsertEventRequest entityRequest) {
        log.info("Update event with ID: {}", id);
        log.info("Update event: {}", entityRequest);

        var updatedEvent = updateFields(repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("Event with ID {0} not found!", id)
                )),
                eventMapper.upsertRequestToEvent(entityRequest));

        log.info("Updated event: {}", updatedEvent);

        return eventMapper.eventToResponse(
                repository.save(updatedEvent)
        );
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete event with ID: {}", id);
        repository.deleteById(id);
    }

    protected Event updateFields(Event oldEntity, Event newEntity) {

        if (StringUtils.hasText(newEntity.getAddress())){
            oldEntity.setAddress(newEntity.getAddress());
        }
        if (newEntity.getDate()!=null){
            oldEntity.setDate(newEntity.getDate());
        }
        if (newEntity.getCloseRegistrationDate()!=null){
            oldEntity.setCloseRegistrationDate(newEntity.getCloseRegistrationDate());
        }
        if (newEntity.getStartRegistrationDate()!=null){
            oldEntity.setStartRegistrationDate(newEntity.getStartRegistrationDate());
        }
        if (StringUtils.hasText(newEntity.getSummary())){
            oldEntity.setSummary(newEntity.getSummary());
        }
        if (StringUtils.hasText(newEntity.getName())){
            oldEntity.setName(newEntity.getName());
        }
        if (newEntity.getRegOpen()!=null){
            oldEntity.setRegOpen(newEntity.getRegOpen());
        }
        if (newEntity.getOrganizer() != null){
            oldEntity.setOrganizer(newEntity.getOrganizer());
        }
        return oldEntity;
    }

    @Override
    public ModelListResponse<EventResponse> findAllByOrganizerId(UUID orgId, Pageable pageable) {
        log.info("Find all events by organizer id: {}",orgId);
        Page<Event> events = repository.findAllByOrganizerId(orgId, pageable);
        return ModelListResponse.<EventResponse>builder()
                .totalCount(events.getTotalElements())
                .data(events.stream().map(eventMapper::eventToResponse).toList())
                .build();
    }

    @Override
    public ModelListResponse<EventResponse> filterBy(EventFilterRequest filter) {
        log.info("Find events with filter: {}",filter);
        Page<Event> events = repository.findAll(EventSpecification.withFilter(filter),
                filter.getPagination().pageRequest());
        return ModelListResponse.<EventResponse>builder()
                .totalCount(events.getTotalElements())
                .data(events.stream().map(eventMapper::eventToResponse).toList())
                .build();
    }


}
