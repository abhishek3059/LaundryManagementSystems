package com.final_project.LaundryManagementSystem.customExceptions;

public class PaymentUnsuccessfulException extends Throwable {
    public PaymentUnsuccessfulException(String paymentCouldNotBeCompleted) {
        super(paymentCouldNotBeCompleted);
    }
}
