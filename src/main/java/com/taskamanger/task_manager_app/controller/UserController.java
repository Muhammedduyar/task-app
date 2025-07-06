package com.taskamanger.task_manager_app.controller;

import com.taskamanger.task_manager_app.dto.UserRequest;
import com.taskamanger.task_manager_app.model.User;
import com.taskamanger.task_manager_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserRequest request) {
        User savedUser = userService.createUser(request);
        return ResponseEntity.ok(savedUser);
    }
}
