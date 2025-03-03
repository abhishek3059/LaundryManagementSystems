package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.dto.PaymentResult;
import com.final_project.LaundryManagementSystem.enums.PaymentMethod;
import org.springframework.stereotype.Service;


public interface PaymentService {
    PaymentResult processPayment(String orderId, PaymentMethod paymentMethod, String paymentDetails) throws OrderNotFoundException;

}
