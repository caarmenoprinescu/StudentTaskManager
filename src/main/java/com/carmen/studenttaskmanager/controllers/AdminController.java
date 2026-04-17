package com.carmen.studenttaskmanager.controllers;

import com.carmen.studenttaskmanager.DTOs.UserDTO;
import com.carmen.studenttaskmanager.models.User;
import com.carmen.studenttaskmanager.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}


