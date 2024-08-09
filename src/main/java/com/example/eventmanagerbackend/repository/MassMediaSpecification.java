package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.entity.MassMedia;
import com.example.eventmanagerbackend.web.dto.request.EventMemberFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.MassMediaFilterRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface MassMediaSpecification {

    static Specification<MassMedia> withFilter(MassMediaFilterRequest filter){
        return Specification.where(byAllFields(filter.getSearchQuery()))
                .and(byEventId(filter.getEventId()))
                .and(byApprovement(filter.getApprovement()));
    }

    static Specification<MassMedia> byAllFields(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(searchQuery)){
                return null;
            }
            String lowerSearchQuery = searchQuery.toLowerCase();

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MassMedia.Fields.company)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MassMedia.Fields.email)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MassMedia.Fields.firstname)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MassMedia.Fields.middlename)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MassMedia.Fields.lastname)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MassMedia.Fields.position)), "%" + lowerSearchQuery + "%"));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    static Specification<MassMedia> byApprovement(Approvement approvement){
        return (root, query, criteriaBuilder) -> {
            if (approvement == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(MassMedia.Fields.approvement),approvement);
        };
    }

    static Specification<MassMedia> byEventId(UUID eventId){
        return (root, query, criteriaBuilder) -> {
            if (eventId == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(MassMedia.Fields.event).get(Event.Fields.id),eventId);
        };
    }

}
