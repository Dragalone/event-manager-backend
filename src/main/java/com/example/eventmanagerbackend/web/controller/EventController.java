package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.web.dto.request.PaginationRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/v1/events",produces = "application/json")
@CrossOrigin()
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ModelListResponse<EventResponse>> getAllEvents(@Valid PaginationRequest paginationRequest){
        List<EventResponse> events = eventService.findAll(paginationRequest.pageRequest());
        return ResponseEntity.ok(
                ModelListResponse.<EventResponse>builder()
                        .totalCount((long) events.size())
                        .data(events)
                        .build()
        );
    }

    @GetMapping("/organizer")
    public ResponseEntity<ModelListResponse<EventResponse>> getAllEventsByOrgId(@Valid PaginationRequest paginationRequest, @RequestParam UUID orgId){
        List<EventResponse> events = eventService.findAllByOrganizerId(orgId, paginationRequest.pageRequest());
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
