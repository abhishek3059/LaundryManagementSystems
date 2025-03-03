package com.final_project.LaundryManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class UserUpdateRequest {
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String address;
}
