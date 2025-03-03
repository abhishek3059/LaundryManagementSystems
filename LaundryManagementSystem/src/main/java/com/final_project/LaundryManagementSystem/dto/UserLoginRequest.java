package com.final_project.LaundryManagementSystem.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
