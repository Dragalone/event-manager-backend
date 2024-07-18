package com.example.eventmanagerbackend.web.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventMemberResponse {

    private UUID id;

    private String firstname;

    private String middlename;

    private String lastname;

    private String company;

    private String position;

    private String email;

    private String phone;

    private Boolean approved;

    private UUID eventId;

}


