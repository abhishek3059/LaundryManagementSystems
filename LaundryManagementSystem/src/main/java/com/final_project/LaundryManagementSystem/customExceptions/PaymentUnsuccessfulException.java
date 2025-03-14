package com.final_project.LaundryManagementSystem.customExceptions;

public class PaymentUnsuccessfulException extends RuntimeException {
    public PaymentUnsuccessfulException(String paymentCouldNotBeCompleted) {
        super(paymentCouldNotBeCompleted);
    }
}
