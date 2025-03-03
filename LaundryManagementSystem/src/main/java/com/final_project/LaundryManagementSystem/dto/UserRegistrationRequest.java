package com.final_project.LaundryManagementSystem.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Set<String> roles = new HashSet<>();
    private String address;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d$",
            message = "Date of birth must be in the format dd-MM-yyyy")
    private String dateOfBirth;


}
