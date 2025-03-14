package com.final_project.LaundryManagementSystem.customExceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String s) {
        super(s);
    }
}
