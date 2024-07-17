package com.example.eventmanagerbackend.web.dto.response;

import com.example.eventmanagerbackend.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;

    private String username;

    private String password;

    private Collection<RoleType> roles;

}
