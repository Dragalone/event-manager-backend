package com.example.eventmanagerbackend.service.template;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, UUID> {
    Optional<TemplateEntity> findByTemplateName(String templateName);

    Optional<TemplateEntity> findByEventidAndTemptype(UUID eventid, Type temptype);

    Iterable<TemplateEntity> findAllByEventid(UUID eventId);
}
