package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.exception.AlreadyExistsException;
import com.example.eventmanagerbackend.repository.UserRepository;
import com.example.eventmanagerbackend.service.AbstractEntityService;
import com.example.eventmanagerbackend.service.UserService;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;
@Service
@Slf4j

public class UserServiceImpl extends AbstractEntityService<User, UUID, UserRepository> implements UserService {

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected User updateFields(User oldEntity, User newEntity) {
        return null;
    }



    @Override
    public User save(User entity) {
        if (repository.existsByUsername(entity.getUsername())){
            throw new AlreadyExistsException(
                    MessageFormat.format("User with email {0} already exists!",  entity.getUsername())
            );
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse createUser(UpsertUserRequest request) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
