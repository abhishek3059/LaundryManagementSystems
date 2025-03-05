package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.dto.PaymentResult;
import com.final_project.LaundryManagementSystem.enums.PaymentMethod;


public interface PaymentService {
    PaymentResult processPayment(Long orderId, PaymentMethod paymentMethod, String paymentDetails) throws OrderNotFoundException;

}
