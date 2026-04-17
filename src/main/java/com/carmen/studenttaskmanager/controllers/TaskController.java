package com.carmen.studenttaskmanager.controllers;


import com.carmen.studenttaskmanager.DTOs.TaskDTO;
import com.carmen.studenttaskmanager.models.Task;
import com.carmen.studenttaskmanager.models.User;
import com.carmen.studenttaskmanager.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Task>> getMyTasks(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(taskService.getTasksByUser(user));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO task, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(taskService.createTask(task, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
