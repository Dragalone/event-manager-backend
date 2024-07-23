package com.example.eventmanagerbackend.web.dto.request;

import com.example.eventmanagerbackend.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertEventRequest {

    @NotNull
    private String name;

    private String summary;

    @NotNull
    private Instant date;

    @NotNull
    private Boolean regOpen;

    @NotNull
    private String address;

    @NotNull
    private Instant startRegistrationDate;

    @NotNull
    private Instant closeRegistrationDate;

    private UUID organizerId;
}
