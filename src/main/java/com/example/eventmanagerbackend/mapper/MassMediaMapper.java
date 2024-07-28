package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.MassMedia;
import com.example.eventmanagerbackend.web.dto.request.UpsertMassMediaRequest;
import com.example.eventmanagerbackend.web.dto.response.MassMediaResponse;
import org.mapstruct.*;

@DecoratedWith(MassMediaMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MassMediaMapper {

    MassMedia upsertRequestToMassMedia(UpsertMassMediaRequest request);

    @Mapping(source = "event.id", target = "eventId")
    MassMediaResponse massMediaToResponse(MassMedia massMedia);
}