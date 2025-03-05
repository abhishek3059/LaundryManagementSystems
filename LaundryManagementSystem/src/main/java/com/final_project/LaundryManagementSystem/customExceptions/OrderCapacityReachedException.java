package com.final_project.LaundryManagementSystem.customExceptions;

public class OrderCapacityReachedException extends Throwable {
    public OrderCapacityReachedException(String s) {
        super(s);
    }
}
