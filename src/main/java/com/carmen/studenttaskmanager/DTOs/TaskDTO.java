package com.carmen.studenttaskmanager.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
