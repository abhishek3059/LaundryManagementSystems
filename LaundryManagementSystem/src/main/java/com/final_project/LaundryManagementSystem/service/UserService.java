package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.customExceptions.UnauthorizedAccessException;
import com.final_project.LaundryManagementSystem.customExceptions.UserAlreadyExistsException;
import com.final_project.LaundryManagementSystem.dto.UserLoginRequest;
import com.final_project.LaundryManagementSystem.dto.UserRegistrationRequest;

public interface UserService {
    boolean registerUser(UserRegistrationRequest request) throws UserAlreadyExistsException, UnauthorizedAccessException;
    String login(UserLoginRequest request);
    boolean validateToken(String token);
    boolean registerAsAdmin(UserRegistrationRequest request) throws UserAlreadyExistsException;
    boolean registerAsStaff(UserRegistrationRequest request) throws UserAlreadyExistsException;
}
