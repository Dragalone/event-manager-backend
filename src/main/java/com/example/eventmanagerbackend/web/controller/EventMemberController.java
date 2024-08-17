package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.aop.AccessCheckType;
import com.example.eventmanagerbackend.aop.Accessible;
import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.service.EventMemberService;
import com.example.eventmanagerbackend.web.dto.request.*;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/v1/event-members",produces = "application/json")
@CrossOrigin()
public class EventMemberController {

    private final EventMemberService eventMemberService;


    //TODO Сделать нормальную защиту эндпоинта filterBy
    @GetMapping
    public ResponseEntity<ModelListResponse<EventMemberResponse>> filterBy(@Valid PaginationRequest paginationRequest,
                                                                     @Nullable @RequestParam String searchQuery,
                                                                     @Nullable @RequestParam Approvement approvement,
                                                                     @Nullable @RequestParam UUID eventId) {
        EventMemberFilterRequest filter = new EventMemberFilterRequest(paginationRequest,searchQuery,approvement,eventId);
        return ResponseEntity.ok(
                eventMemberService.filterBy(filter)
        );
    }

    @GetMapping("/{id}")
    @Accessible(checkBy = AccessCheckType.EVENT_MEMBER, availableForAdmin = true)
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
        //TODO ПЕРЕПИСАТЬ ЛОГИКУ ДЕФОЛТНОГО ЗНАЧЕНИЯ ДЛЯ СТАТУСА
        request.setStatusId(UUID.fromString("2e0b5137-ec98-4632-bbe6-2b867ded745d"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventMemberService.createMemberOnConsideration(request));
    }

    @PutMapping("/{id}")
    @Accessible(checkBy = AccessCheckType.EVENT_MEMBER, availableForAdmin = true)
    public ResponseEntity<EventMemberResponse> updateEventMember(@PathVariable UUID id, @RequestBody UpsertEventMemberRequest request){
        System.out.println(request.getStatusId());
        return ResponseEntity.ok(
                eventMemberService.update(id,request)
        );
    }

    @DeleteMapping("/{id}")
    @Accessible(checkBy = AccessCheckType.EVENT_MEMBER, availableForAdmin = true)
    public ResponseEntity<Void> deleteEventMember(@PathVariable UUID id){
        eventMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/approvement/{id}")
    @Accessible(checkBy = AccessCheckType.EVENT_MEMBER, availableForAdmin = true)
    public ResponseEntity<EventResponse> setEventMemberApprovement(@PathVariable UUID id, @RequestParam String approvement){
        eventMemberService.setApprovment(id, Approvement.valueOf(approvement));
        return ResponseEntity.noContent().build();
    }


}
