package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.entity.Waiter;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertOnConsiderationEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.WaiterRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.WaiterResponse;
import org.mapstruct.*;

@DecoratedWith(WaiterMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WaiterMapper {
    Waiter upsertRequestToWaiter(WaiterRequest request);
    @Mapping(source = "event.id", target = "eventId")
    WaiterResponse WaiterToResponse(Waiter waiter);

}