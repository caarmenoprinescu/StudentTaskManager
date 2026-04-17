package com.carmen.studenttaskmanager.services;

import com.carmen.studenttaskmanager.DTOs.UserDTO;
import com.carmen.studenttaskmanager.models.User;
import com.carmen.studenttaskmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); newUser.setRole(user.getRole());

        return userRepository.save(newUser);

    }
}
