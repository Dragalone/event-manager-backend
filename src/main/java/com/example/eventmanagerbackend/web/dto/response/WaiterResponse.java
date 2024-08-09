package com.example.eventmanagerbackend.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterResponse {

    private UUID id;

    private String email;

    private UUID eventId;
}
