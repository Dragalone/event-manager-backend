package com.example.eventmanagerbackend.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertOnConsiderationEventMemberRequest {


    @NotNull
    private String firstname;

    @NotNull
    private String middlename;

    @NotNull
    private String lastname;

    @NotNull
    private String company;

    @NotNull
    private String position;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private UUID eventId;

}
