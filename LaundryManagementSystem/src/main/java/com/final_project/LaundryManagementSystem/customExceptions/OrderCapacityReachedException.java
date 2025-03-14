package com.final_project.LaundryManagementSystem.customExceptions;

public class OrderCapacityReachedException extends RuntimeException {
    public OrderCapacityReachedException(String s) {
        super(s);
    }
}
