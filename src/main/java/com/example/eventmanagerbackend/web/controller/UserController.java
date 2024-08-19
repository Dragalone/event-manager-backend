package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.RoleType;
import com.example.eventmanagerbackend.repository.RoleTypeRepository;
import com.example.eventmanagerbackend.repository.UserRepository;
import com.example.eventmanagerbackend.service.UserService;
import com.example.eventmanagerbackend.web.dto.request.PaginationRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertDefaultUserRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="api/v1/users",produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin()
public class UserController {

    private final UserService userService;
    private final RoleTypeRepository roleTypeRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserResponse> createDefaultUser(@RequestBody UpsertDefaultUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createDefaultUser(request));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ORGANIZATOR')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UpsertUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    @PutMapping("/admin")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpsertUserRequest request, @RequestParam UUID id){

        return ResponseEntity.ok(userService.update(id,request));
    }


    @DeleteMapping("/admin")
    public ResponseEntity<Void> deleteUser(@RequestParam UUID id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //TODO
    // Переписать под фильтры
    @GetMapping
    public ResponseEntity<ModelListResponse<UserResponse>> getAllUsers(@Valid PaginationRequest paginationRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.findAll(paginationRequest.pageRequest()));
    }

    //TODO
    // Переписать
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(userService.findById(id));
    }

    //TODO
    // Переписать
    @GetMapping("/getRoles")
    public ResponseEntity<List<RoleType>> getAllRoles(){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleTypeRepository.findAll());
    }



}
