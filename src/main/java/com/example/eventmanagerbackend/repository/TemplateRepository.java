package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.TemplateEntity;
import com.example.eventmanagerbackend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, UUID> {


    Optional<TemplateEntity> findByTemplateName(String templateName);

    Optional<TemplateEntity> findByEventIdAndTemptype(UUID eventid, Type temptype);

    Iterable<TemplateEntity> findAllByEventId(UUID eventId);
}
