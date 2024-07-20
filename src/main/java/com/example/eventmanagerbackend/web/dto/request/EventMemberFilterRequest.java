package com.example.eventmanagerbackend.web.dto.request;

import com.example.eventmanagerbackend.entity.Approvement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMemberFilterRequest {

    @NotNull
    private PaginationRequest pagination;

    private String searchQuery;

    private Approvement approvement;

    private UUID eventId;

}
