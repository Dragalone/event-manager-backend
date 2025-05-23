package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.Waiter;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.mapper.WaiterMapper;
import com.example.eventmanagerbackend.repository.WaiterRepository;
import com.example.eventmanagerbackend.service.WaiterService;
import com.example.eventmanagerbackend.web.dto.request.WaiterRequest;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import com.example.eventmanagerbackend.web.dto.response.WaiterResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;
@Service
@Data
@AllArgsConstructor
@Slf4j
public class WaiterServiceImpl implements WaiterService {

    private final WaiterRepository repository;

    private final WaiterMapper waiterMapper;
    @Override
    public ModelListResponse<WaiterResponse> findAll(Pageable pageable) {
        log.info("Find all Waiters");
        Page<Waiter> waiter = repository.findAll(pageable);
        return ModelListResponse.<WaiterResponse>builder()
                .totalCount(waiter.getTotalElements())
                .data(waiter.stream().map(waiterMapper::WaiterToResponse).toList())
                .build();
    }

    @Override
    public WaiterResponse findById(UUID id) {
        log.info("Find waiter by ID: {}", id);
        return waiterMapper.WaiterToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Waiter with ID {0} not founded", id)
        )));
    }

    @Override
    public WaiterResponse create(WaiterRequest entityRequest) {
        log.info("Creating Waiter {}", entityRequest);

        return waiterMapper.WaiterToResponse
                (repository.save
                        (waiterMapper.upsertRequestToWaiter
                                (entityRequest)));
    }

    @Override
    public WaiterResponse update(UUID id, WaiterRequest entityRequest) {
        log.info("Updating waiter with ID {}", id);

        return null;
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Deleting waiter with ID {}", id);
        repository.deleteById(id);
    }

    @Override
    public ModelListResponse<WaiterResponse> findWaitersByEventId(UUID id, Pageable pageable) {
        log.info("Find all waiters in event {}", id);
        Page<Waiter> waiters = repository.findAllByEventId(id, pageable);
        return     ModelListResponse.<WaiterResponse>builder().
                totalCount(waiters.getTotalElements())
                .data(waiters.stream().map(waiterMapper::WaiterToResponse).toList())
                .build();
    }
}
