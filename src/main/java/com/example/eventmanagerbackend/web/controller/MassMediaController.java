package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.service.MassMediaService;
import com.example.eventmanagerbackend.web.dto.request.UpsertMassMediaRequest;
import com.example.eventmanagerbackend.web.dto.response.MassMediaResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
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
    public ResponseEntity<ModelListResponse<MassMediaResponse>> filterBy()
    {
        return null;
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

}
