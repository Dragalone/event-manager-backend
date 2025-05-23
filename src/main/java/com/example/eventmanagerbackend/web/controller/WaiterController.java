package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.aop.AccessCheckType;
import com.example.eventmanagerbackend.aop.Accessible;
import com.example.eventmanagerbackend.service.WaiterService;
import com.example.eventmanagerbackend.web.dto.request.UpsertEventRequest;
import com.example.eventmanagerbackend.web.dto.request.WaiterRequest;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import com.example.eventmanagerbackend.web.dto.response.WaiterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/waiters", produces = "application/json")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class WaiterController {

    private final WaiterService waiterService;

    @GetMapping
    public ResponseEntity<ModelListResponse<WaiterResponse>> getAll(Pageable pageable)
    {
        return ResponseEntity.ok(waiterService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaiterResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(
                waiterService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<WaiterResponse> createWaiter(@RequestBody WaiterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(waiterService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id){
        waiterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
