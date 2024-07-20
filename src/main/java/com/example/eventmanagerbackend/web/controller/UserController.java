package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.service.UserService;
import com.example.eventmanagerbackend.web.dto.request.UpsertDefaultUserRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertUserRequest;
import com.example.eventmanagerbackend.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="api/v1/users",produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin()
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createDefaultUser(@RequestBody UpsertDefaultUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createDefaultUser(request));
    }

    @PostMapping("/admin")
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


}
