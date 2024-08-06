package com.example.eventmanagerbackend.service;


import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.web.dto.request.EventMemberFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertOnConsiderationEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EventMemberService extends EntityService<EventMemberResponse, UpsertEventMemberRequest, UUID> {

    void setApprovment(UUID id, Approvement approvement);
    public ModelListResponse<EventMemberResponse> filterBy(EventMemberFilterRequest filter);

    EventMemberResponse createMemberOnConsideration(UpsertOnConsiderationEventMemberRequest entityRequest);



    ModelListResponse<EventMemberResponse> findMembersByEventId(UUID eventId, Pageable pageable);

}
