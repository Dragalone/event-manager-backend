package com.example.eventmanagerbackend.service.impl;


import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.exception.RegistrationClosedException;
import com.example.eventmanagerbackend.mapper.EventMemberMapper;
import com.example.eventmanagerbackend.repository.EventMemberRepository;

import com.example.eventmanagerbackend.repository.EventMemberSpecification;
import com.example.eventmanagerbackend.repository.EventSpecification;
import com.example.eventmanagerbackend.service.EventMemberService;

import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.EventMemberFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertOnConsiderationEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventMemberServiceImpl implements EventMemberService {

    private final EventMemberMapper eventMemberMapper;

    private final EventMemberRepository repository;



    @Override
    public ModelListResponse<EventMemberResponse>filterBy(EventMemberFilterRequest filter) {
        log.info("Find events members with filter: {}",filter);
        Page<EventMember> eventMembers = repository.findAll(EventMemberSpecification.withFilter(filter), filter.getPagination().pageRequest());
        return ModelListResponse.<EventMemberResponse>builder()
                .totalCount(eventMembers.getTotalElements())
                .data(eventMembers.stream().map(eventMemberMapper::eventMemberToResponse).toList())
                .build();
    }

    @Override
    public ModelListResponse<EventMemberResponse> findAll(Pageable pageable) {
        log.info("Find all event members");
        Page<EventMember> eventMembers = repository.findAll(pageable);
        return ModelListResponse.<EventMemberResponse>builder()
                .totalCount(eventMembers.getTotalElements())
                .data(eventMembers.stream().map(eventMemberMapper::eventMemberToResponse).toList())
                .build();
    }

    @Override
    public EventMemberResponse findById(UUID id) {
        log.info("Find event member with ID: {}", id);
        return eventMemberMapper.eventMemberToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Event member with ID {0} not found!", id)
                )));
    }

    @Override
    public EventMemberResponse create(UpsertEventMemberRequest entityRequest) {
        log.info("Create event member {}",entityRequest);
        EventMember eventMember = eventMemberMapper.upsertRequestToEventMember(entityRequest);
        if (!eventMember.getEvent().getRegOpen()) {
            throw new RegistrationClosedException(
                    MessageFormat.format("Registration on event {0} closed!",eventMember.getEvent().getName())
            );
        }
        return eventMemberMapper.eventMemberToResponse(
                repository.save(eventMemberMapper.upsertRequestToEventMember(entityRequest))
        );
    }

    @Override
    public EventMemberResponse createMemberOnConsideration(UpsertOnConsiderationEventMemberRequest entityRequest) {
        log.info("Create event member on consideration {}",entityRequest);
        EventMember eventMember = eventMemberMapper.notApprovedUpsertRequestToEventMember(entityRequest);
        if (!eventMember.getEvent().getRegOpen()) {
            throw new RegistrationClosedException(
                    MessageFormat.format("Registration on event {0} closed!",eventMember.getEvent().getName())
            );
        }
        eventMember.setApprovement(Approvement.CONSIDERATION);
        return eventMemberMapper.eventMemberToResponse(
                repository.save(eventMember)
        );
    }

    @Override
    public ModelListResponse<EventMemberResponse> findMembersByEventId(UUID event, Pageable pageable) {
        log.info("Find all members in event: {}", event);
        Page<EventMember> eventMembers = repository.findAllByEventId(event, pageable);
        return  ModelListResponse.<EventMemberResponse>builder()
                .totalCount(eventMembers.getTotalElements())
                .data(eventMembers.stream().map(eventMemberMapper::eventMemberToResponse).toList())
                .build();
    }

    @Override
    public EventMemberResponse update(UUID id, UpsertEventMemberRequest entityRequest) {
        log.info("Update event member with ID: {}", id);

        var updatedEventMember = updateFields(repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("Event member with ID {0} not found!", id)
                        )),
                eventMemberMapper.upsertRequestToEventMember(entityRequest));

        log.info("Updated event member: {}", updatedEventMember);

        return eventMemberMapper.eventMemberToResponse(
                repository.save(updatedEventMember)
        );
    }



    @Override
    public void deleteById(UUID id) {
        log.info("Delete event member with ID: {}", id);
        repository.deleteById(id);
    }

    protected EventMember updateFields(EventMember oldEntity, EventMember newEntity) {
        if (StringUtils.hasText(newEntity.getCompany())){
            oldEntity.setCompany(newEntity.getCompany());
        }
        if (StringUtils.hasText(newEntity.getEmail())){
            oldEntity.setEmail(newEntity.getEmail());
        }
        if (StringUtils.hasText(newEntity.getFirstname())){
            oldEntity.setFirstname(newEntity.getFirstname());
        }
        if (StringUtils.hasText(newEntity.getMiddlename())){
            oldEntity.setMiddlename(newEntity.getMiddlename());
        }
        if (StringUtils.hasText(newEntity.getLastname())){
            oldEntity.setLastname(newEntity.getLastname());
        }
        if (StringUtils.hasText(newEntity.getPhone())){
            oldEntity.setPhone(newEntity.getPhone());
        }
        if (StringUtils.hasText(newEntity.getPosition())){
            oldEntity.setPosition(newEntity.getPosition());
        }
        if (newEntity.getEvent()!=null){
            oldEntity.setEvent(newEntity.getEvent());
        }
        if (newEntity.getApprovement()!=null){
            oldEntity.setApprovement(newEntity.getApprovement());
        }
        if (newEntity.getStatus()!=null){
            oldEntity.setStatus(newEntity.getStatus());
        }
        return oldEntity;
    }

    @Override
    public void setApprovment(UUID id, Approvement approvement) {
        log.info("Set {} approvment for member with id: {}", approvement, id);
        EventMember member = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Event member with ID {0} not found!", id)
                ));
        member.setApprovement(approvement);
        repository.save(member);
    }

}
