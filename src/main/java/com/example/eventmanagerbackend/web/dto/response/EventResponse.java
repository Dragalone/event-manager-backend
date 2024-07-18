package com.example.eventmanagerbackend.web.dto.response;

import com.example.eventmanagerbackend.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {

    private UUID eventId;

    private String eventName;

    private String eventSummary;

    private Instant eventDate;

    private Boolean regOpen;

    private String address;

    private Instant startRegistrationDate;

    private Instant closeRegistrationDate;

    private UserResponse organizer;
}
