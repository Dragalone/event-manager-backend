package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, UUID>, JpaSpecificationExecutor<EventMember> {

    Page<EventMember> findAllByEventId(UUID eventId, Pageable pageable);

    Page<EventMember> findByEventIdAndApprovement(UUID eventId, Approvement approvement, Pageable pageable);

}