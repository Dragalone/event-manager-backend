package com.example.eventmanagerbackend.mapper;

import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    User upsertRequestToUser(UpsertUserRequest upsertUserRequest);

    UserResponse userToResponse (User user);

}
