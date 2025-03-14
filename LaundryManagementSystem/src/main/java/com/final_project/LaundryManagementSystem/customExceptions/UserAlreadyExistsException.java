package com.final_project.LaundryManagementSystem.customExceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
