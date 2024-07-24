package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.RoleType;
import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.exception.AlreadyExistsException;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.mapper.UserMapper;
import com.example.eventmanagerbackend.repository.RoleTypeRepository;
import com.example.eventmanagerbackend.repository.UserRepository;

import com.example.eventmanagerbackend.service.UserService;
import com.example.eventmanagerbackend.web.dto.request.UpsertDefaultUserRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserRepository repository;

    private final RoleTypeRepository roleTypeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("User with username {0} not found!", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Override
    public UserResponse findByUsername(String username) {
        log.info("Find user with username: {}", username);
        return userMapper.userToResponse(repository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("User with username {0} not found!",username)))
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public ModelListResponse<UserResponse> findAll(Pageable pageable) {
        log.info("Find all users");
        Page<User> users = repository.findAll(pageable);
        return ModelListResponse.<UserResponse>builder()
                .totalCount(users.getTotalElements())
                .data(users.stream().map(userMapper::userToResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserResponse findById(UUID id) {
        log.info("Find user with ID: {}", id);
        return userMapper.userToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("User with ID {0} not found!", id)
                )));
    }

    @Override
    public UserResponse createDefaultUser(UpsertDefaultUserRequest upsertDefaultUserRequest) {
        log.info("Create default user {}",upsertDefaultUserRequest);
        if (repository.existsByUsername(upsertDefaultUserRequest.getUsername())){
            throw new AlreadyExistsException(
                    MessageFormat.format("User with username {0} already exists!",  upsertDefaultUserRequest.getUsername())
            );
        }

        RoleType userRole = roleTypeRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(upsertDefaultUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(upsertDefaultUserRequest.getPassword()));
        user.setRoles(Collections.singletonList(userRole));
        return userMapper.userToResponse(repository.save(user));
    }

    @Override
    public UserResponse create(UpsertUserRequest entityRequest) {
        log.info("Create user {}",entityRequest);
        if (repository.existsByUsername(entityRequest.getUsername())){
            throw new AlreadyExistsException(
                    MessageFormat.format("User with username {0} already exists!",  entityRequest.getUsername())
            );
        }

        User user = new User();
        user.setUsername(entityRequest.getUsername());
        user.setPassword(passwordEncoder.encode(entityRequest.getPassword()));
        user.setRoles(entityRequest.getRoles());
        return userMapper.userToResponse(repository.save(user));
    }

    @Override
    public UserResponse update(UUID id, UpsertUserRequest entityRequest) {
        log.info("Update user with ID: {}", id);

        var updatedUser = updateFields(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("User with ID {0} not found!", id)
                )),
                userMapper.upsertRequestToUser(entityRequest));

        log.info("Updated user: {}", updatedUser);

        return userMapper.userToResponse(repository.save(updatedUser));
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete user with ID: {}", id);
        repository.deleteById(id);
    }

    protected User updateFields(User oldEntity, User newEntity) {
        if (!Objects.equals(oldEntity.getUsername(), newEntity.getUsername()) && existsByUsername(newEntity.getUsername())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("User with username {0} already exists!",  newEntity.getUsername())
            );
        } else{
            oldEntity.setUsername(newEntity.getUsername());
        }
        if (StringUtils.hasText(newEntity.getPassword())){
            oldEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
        }
        if (newEntity.getRoles()!=null){
            oldEntity.setRoles(newEntity.getRoles());
        }
        return oldEntity;
    }
}