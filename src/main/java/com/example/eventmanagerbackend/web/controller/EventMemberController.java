package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.service.EventMemberService;
import com.example.eventmanagerbackend.web.dto.request.PaginationRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertOnConsiderationEventMemberRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
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
@RequestMapping(path="api/v1/event-members",produces = "application/json")
@CrossOrigin()
public class EventMemberController {

    private final EventMemberService eventMemberService;

    @GetMapping
    public ResponseEntity<ModelListResponse<EventMemberResponse>> getAllEventMembers(@Valid PaginationRequest pageRequest){
        List<EventMemberResponse> eventMembers = eventMemberService.findAll(pageRequest.pageRequest());
        return ResponseEntity.ok(
                ModelListResponse.<EventMemberResponse>builder()
                        .totalCount((long) eventMembers.size())
                        .data(eventMembers)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventMemberResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(
                eventMemberService.findById(id)
        );
    }

    @PostMapping("/admin")
    public ResponseEntity<EventMemberResponse> createEventMember(@RequestBody UpsertEventMemberRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventMemberService.create(request));
    }

    @PostMapping
    public ResponseEntity<EventMemberResponse> createEventMemberOnConsideration(@RequestBody UpsertOnConsiderationEventMemberRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventMemberService.createMemberOnConsideration(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventMemberResponse> updateEventMember(@PathVariable UUID id, @RequestBody UpsertEventMemberRequest request){
        return ResponseEntity.ok(
                eventMemberService.update(id,request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventMember(@PathVariable UUID id){
        eventMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/approvement/{id}")
    public ResponseEntity<EventResponse> setEventMemberApprovement(@PathVariable UUID id, @RequestParam String approvement){
        eventMemberService.setApprovment(id, Approvement.valueOf(approvement));
        return ResponseEntity.noContent().build();
    }
}
