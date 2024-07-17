package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, UUID> {

    Page<EventMember> findAllByEventId(UUID eventId, Pageable pageable);

    Page<EventMember> findByEventIdAndApproved(UUID eventId, Boolean approved, Pageable pageable);

}
