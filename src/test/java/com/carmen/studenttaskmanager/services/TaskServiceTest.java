package com.carmen.studenttaskmanager.services;

import com.carmen.studenttaskmanager.DTOs.TaskDTO;
import com.carmen.studenttaskmanager.models.Role;
import com.carmen.studenttaskmanager.models.Task;
import com.carmen.studenttaskmanager.models.User;
import com.carmen.studenttaskmanager.repositories.TaskRepository;
import com.carmen.studenttaskmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTaskWithCorrectOwner() {

        User user = new User();
        user.setUsername("test user");
        user.setPassword("pass");
        user.setRole(Role.ROLE_USER);

        TaskDTO task = new TaskDTO();
        task.setTitle("title test");
        task.setDescription("description test");

        Task savedTask = new Task();
        savedTask.setTitle("title test");
        savedTask.setDescription("description test");
        savedTask.setOwner(user);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);


        Task result = taskService.createTask(task, user);


        assertThat(result.getOwner()).isEqualTo(user);
        assertThat(result.getTitle()).isEqualTo("title test");

    }

    @Test
    void shouldReturnTasksForUser() {
        User user = new User();
        user.setUsername("test user");
        user.setRole(Role.ROLE_USER);

        Task task1 = new Task();
        task1.setOwner(user);
        task1.setTitle("task 1");

        Task task2 = new Task();
        task2.setOwner(user);
        task2.setTitle("task 2");

        when(taskRepository.findByOwner(user)).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getTasksByUser(user);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOwner()).isEqualTo(user);
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task();
        task1.setTitle("task 1");

        Task task2 = new Task();
        task2.setTitle("task 2");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getAllTasks();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldDeleteTaskById() {
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);
    }

}