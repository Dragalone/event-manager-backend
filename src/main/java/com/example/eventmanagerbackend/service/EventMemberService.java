package com.example.eventmanagerbackend.service;


import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertOnConsiderationEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;

import java.util.UUID;

public interface EventMemberService extends EntityService<EventMemberResponse, UpsertEventMemberRequest, UUID> {
    void setApprovment(UUID id, Approvement approvement);

    EventMemberResponse createMemberOnConsideration(UpsertOnConsiderationEventMemberRequest entityRequest);
}
