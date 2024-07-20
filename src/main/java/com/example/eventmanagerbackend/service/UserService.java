package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.web.dto.request.UpsertDefaultUserRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService extends EntityService<UserResponse,UpsertUserRequest, UUID> {
    UserResponse findByUsername(String username);

    UserResponse createDefaultUser(UpsertDefaultUserRequest upsertDefaultUserRequest);

    boolean existsByUsername(String username);

}
