package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.service.WaiterService;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import com.example.eventmanagerbackend.web.dto.response.WaiterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/waiters", produces = "application/json")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class WaiterController {

    private final WaiterService waiterService;
    //TODO НАПИСАТЬ ЗАПРОСЫ ДЛЯ АДМИН ПАНЕЛИ
    @GetMapping
    public ResponseEntity<ModelListResponse<WaiterResponse>> getAll(Pageable pageable)
    {
        return ResponseEntity.ok(waiterService.findAll(pageable));
    }
}
