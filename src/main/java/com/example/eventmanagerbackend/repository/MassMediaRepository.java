package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.MassMedia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MassMediaRepository extends JpaRepository<MassMedia, UUID>, JpaSpecificationExecutor<MassMedia> {

    Page<MassMedia> findAllByByEventId(UUID eventId, Pageable pageable);

    Page<MassMedia> findByEventIdAndApprovement(UUID eventId, Approvement approvement, Pageable pageable);
}
