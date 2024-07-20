package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.UUID;

public interface EventSpecification {

    static Specification<Event> withFilter(EventFilterRequest filter){
        return Specification.where(byName(filter.getName()))
                .and(byLowerDateLimit(filter.getLowerDateLimit()))
                .and((byHigherDateLimit(filter.getHigherDateLimit())))
                .and(byOrgId(filter.getOrgId()))
                .and(byRegOpen(filter.getRegOpen()));
    }

    static Specification<Event> byRegOpen(Boolean regOpen){
        return (root, query, criteriaBuilder) -> {
            if (regOpen == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(Event.Fields.regOpen),regOpen);
        };
    }

    static Specification<Event> byOrgId(UUID orgId){
        return (root, query, criteriaBuilder) -> {
            if (orgId == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(Event.Fields.organizer).get(User.Fields.id),orgId);
        };
    }
    static Specification<Event> byName(String name){
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(name)){
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(Event.Fields.name)), "%" + name.toLowerCase() + "%");
        };
    }

    static Specification<Event> byLowerDateLimit(Instant lowerDate){
        return (root, query, criteriaBuilder) -> {
            if (lowerDate == null){
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Event.Fields.date),lowerDate);
        };
    }

    static Specification<Event> byHigherDateLimit(Instant higherDate){
        return (root, query, criteriaBuilder) -> {
            if (higherDate == null){
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(Event.Fields.date),higherDate);
        };
    }
}
