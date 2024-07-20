package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.PaginationRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/v1/events",produces = "application/json")
@CrossOrigin()
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ModelListResponse<EventResponse>> filterBy(@Valid PaginationRequest paginationRequest,
                                                                     @Nullable @RequestParam String name,
                                                                     @Nullable @RequestParam Instant lowerDateLimit,
                                                                     @Nullable @RequestParam Instant higherDateLimit,
                                                                     @Nullable @RequestParam Boolean regOpen,
                                                                     @Nullable @RequestParam UUID orgId) {
        EventFilterRequest filter = new EventFilterRequest(paginationRequest, name, lowerDateLimit, higherDateLimit,regOpen, orgId);
        List<EventResponse> events = eventService.filterBy(filter);
        return ResponseEntity.ok(
                ModelListResponse.<EventResponse>builder()
                        .totalCount((long) events.size())
                        .data(events)
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(
                eventService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody UpsertEventRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable UUID id, @RequestBody UpsertEventRequest request){
        return ResponseEntity.ok(
                eventService.update(id,request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id){
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
