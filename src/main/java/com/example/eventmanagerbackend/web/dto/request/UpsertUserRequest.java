package com.example.eventmanagerbackend.web.dto.request;

import com.example.eventmanagerbackend.entity.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Collection<RoleType> roles;
}
