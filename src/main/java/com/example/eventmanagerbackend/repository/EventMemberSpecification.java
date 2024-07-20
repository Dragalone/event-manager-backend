package com.example.eventmanagerbackend.repository;


import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.web.dto.request.EventFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.EventMemberFilterRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface EventMemberSpecification {



    static Specification<EventMember> withFilter(EventMemberFilterRequest filter){
        return Specification.where(byAllFields(filter.getSearchQuery()))
                .and(byEventId(filter.getEventId()))
                .and(byApprovement(filter.getApprovement()));
    }

     static Specification<EventMember> byAllFields(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(searchQuery)){
                return null;
            }
            String lowerSearchQuery = searchQuery.toLowerCase();

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.company)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.phone)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.email)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.firstname)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.middlename)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.lastname)), "%" + lowerSearchQuery + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventMember.Fields.position)), "%" + lowerSearchQuery + "%"));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    static Specification<EventMember> byApprovement(Approvement approvement){
        return (root, query, criteriaBuilder) -> {
            if (approvement == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(EventMember.Fields.approvement),approvement);
        };
    }

    static Specification<EventMember> byEventId(UUID eventId){
        return (root, query, criteriaBuilder) -> {
            if (eventId == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(EventMember.Fields.event).get(Event.Fields.id),eventId);
        };
    }


}