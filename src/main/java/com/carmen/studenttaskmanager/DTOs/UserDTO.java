package com.carmen.studenttaskmanager.DTOs;

import com.carmen.studenttaskmanager.models.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private Role role;
}
