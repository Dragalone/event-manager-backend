package com.example.eventmanagerbackend.repository;

import com.example.eventmanagerbackend.entity.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByLogin(String username);

    Optional<User> findUserById(UUID id);
}
