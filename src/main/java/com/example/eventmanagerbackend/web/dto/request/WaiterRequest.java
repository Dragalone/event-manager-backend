package com.example.eventmanagerbackend.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterRequest {

    @NotNull
    private String email;

    @NotNull
    private UUID eventId;
}
