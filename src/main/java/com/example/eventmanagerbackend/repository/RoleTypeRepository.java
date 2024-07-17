package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, UUID> {
    Optional<RoleType> findByName(String Name);

}
