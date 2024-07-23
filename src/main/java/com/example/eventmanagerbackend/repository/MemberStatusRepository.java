package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MemberStatusRepository extends JpaRepository<MemberStatus, UUID> {
}
