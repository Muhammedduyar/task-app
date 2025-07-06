package com.taskamanger.task_manager_app.dto;

import com.taskamanger.task_manager_app.model.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private Role role;
}
