package com.example.eventmanagerbackend.service.impl;


import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.mapper.EventMapper;
import com.example.eventmanagerbackend.mapper.EventMemberMapper;
import com.example.eventmanagerbackend.repository.EventMemberRepository;

import com.example.eventmanagerbackend.service.EventMemberService;

import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventMemberServiceImpl implements EventMemberService {

    private final EventMemberMapper eventMemberMapper;

    private final EventMemberRepository repository;

    @Override
    public List<EventMemberResponse> findAll(Pageable pageable) {
        log.info("Find all event members");
        return repository.findAll(pageable)
                .stream().map(eventMemberMapper::eventMemberToResponse)
                .toList();
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
        return eventMemberMapper.eventMemberToResponse(
                repository.save(eventMemberMapper.upsertRequestToEventMember(entityRequest))
        );
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
        if (newEntity.getApproved()!=null){
            oldEntity.setApproved(newEntity.getApproved());
        }
        return oldEntity;
    }
}
