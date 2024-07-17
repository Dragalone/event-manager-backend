package com.example.eventmanagerbackend.service;


import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;

import java.util.UUID;

public interface EventMemberService extends EntityService<EventMember, UUID> {
    EventMemberResponse createEventMember(UpsertEventRequest request, UUID event);


}
