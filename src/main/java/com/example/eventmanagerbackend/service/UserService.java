package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService extends EntityService<User, UUID> {
    UserResponse findByUsername(String username);

    UserResponse createUser(UpsertUserRequest request);

    boolean existsByUsername(String username);


}
