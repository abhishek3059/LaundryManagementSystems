package com.final_project.LaundryManagementSystem.customExceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String s) {
        super(s);
    }
}
