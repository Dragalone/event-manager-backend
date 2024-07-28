package com.example.eventmanagerbackend.web.dto.request;

import com.example.eventmanagerbackend.entity.Approvement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertMassMediaRequest {

    @NotNull
    private String firstname;

    @NotNull
    private String middlename;

    @NotNull
    private String lastname;

    @NotNull
    private String passportSeries;

    @NotNull
    private String passportNumber;

    @NotNull
    private String company;

    @NotNull
    private String position;

    @NotNull
    private String equipment;

    @NotNull
    private String email;

    @NotNull
    private Approvement approvement;

    @NotNull
    private UUID eventId;
}
