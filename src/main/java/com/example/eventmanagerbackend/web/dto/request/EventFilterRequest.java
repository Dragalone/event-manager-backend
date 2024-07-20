package com.example.eventmanagerbackend.web.dto.request;

import com.example.eventmanagerbackend.entity.Approvement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFilterRequest {

    @NotNull
    private PaginationRequest pagination;

    private String name;

    private Instant lowerDateLimit;

    private Instant higherDateLimit;

    private UUID orgId;
}
