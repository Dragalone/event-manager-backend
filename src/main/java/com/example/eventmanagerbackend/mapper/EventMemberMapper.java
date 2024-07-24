package com.example.eventmanagerbackend.mapper;


import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;

import com.example.eventmanagerbackend.web.dto.request.UpsertOnConsiderationEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;

import org.mapstruct.*;

@DecoratedWith(EventMemberMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventMemberMapper {
    EventMember upsertRequestToEventMember(UpsertEventMemberRequest request);
    EventMember notApprovedUpsertRequestToEventMember(UpsertOnConsiderationEventMemberRequest request);
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "status.status", target = "statusName")
    EventMemberResponse eventMemberToResponse(EventMember eventMember);

}
