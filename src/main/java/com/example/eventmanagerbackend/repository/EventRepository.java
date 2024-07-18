package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findAllByOrganizerId(UUID id, Pageable pageable);
}
