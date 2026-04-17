package com.carmen.studenttaskmanager.services;

import com.carmen.studenttaskmanager.DTOs.TaskDTO;
import com.carmen.studenttaskmanager.models.Task;
import com.carmen.studenttaskmanager.models.User;
import com.carmen.studenttaskmanager.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByOwner(user);
    }

    public Task createTask(TaskDTO newTask, User user) {
        Task task = new Task();
        task.setOwner(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        return taskRepository.save(task);
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
