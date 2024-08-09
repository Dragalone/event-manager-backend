package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.Waiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WaiterRepository extends JpaRepository<Waiter, UUID> {

    Page<Waiter> findAllByEventId(UUID eventId, Pageable pageable);
}
