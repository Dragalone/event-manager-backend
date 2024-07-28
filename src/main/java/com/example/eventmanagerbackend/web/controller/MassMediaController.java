package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.service.MassMediaService;
import com.example.eventmanagerbackend.web.dto.request.EventMemberFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.MassMediaFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.PaginationRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertMassMediaRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.MassMediaResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/media", produces = "application/json")
@CrossOrigin
@RequiredArgsConstructor
public class MassMediaController {

    private final MassMediaService massMediaService;

    @GetMapping
    public ResponseEntity<ModelListResponse<MassMediaResponse>> filterBy(@Valid PaginationRequest paginationRequest,
                                                                         @Nullable @RequestParam String searchQuery,
                                                                         @Nullable @RequestParam Approvement approvement,
                                                                         @Nullable @RequestParam UUID eventId)
    {
        MassMediaFilterRequest filter = new MassMediaFilterRequest(paginationRequest,searchQuery,approvement,eventId);
        return ResponseEntity.ok(
                massMediaService.filterBy(filter)
        );
    };

    @GetMapping("/{id}")
    public ResponseEntity<MassMediaResponse> getById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(
                massMediaService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MassMediaResponse> updateMassMedia(@PathVariable UUID id,
                                                             @RequestBody UpsertMassMediaRequest request)
    {
        return ResponseEntity.ok(
                massMediaService.update(id, request)
        );
    }

    @PostMapping
    public ResponseEntity<MassMediaResponse> createMassMedia(@RequestBody UpsertMassMediaRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(massMediaService.create(request));
    }
    @PutMapping("/approvement/{id}")
    public ResponseEntity<EventResponse> setEventMemberApprovement(@PathVariable UUID id, @RequestParam String approvement){
        massMediaService.setApprovment(id, Approvement.valueOf(approvement));
        return ResponseEntity.noContent().build();
    }


}
