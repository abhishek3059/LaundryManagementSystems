package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.customExceptions.OrderCapacityReachedException;
import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.customExceptions.PaymentUnsuccessfulException;
import com.final_project.LaundryManagementSystem.customExceptions.SlotsNotAvailableException;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderDTO;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderRequest;

import javax.security.auth.login.CredentialException;
import java.util.List;

public interface LaundryOrderService {
     LaundryOrderDTO createOrder(LaundryOrderRequest laundryOrderRequest) throws OrderNotFoundException, SlotsNotAvailableException, PaymentUnsuccessfulException, OrderCapacityReachedException;
     LaundryOrderDTO updateOrderStatus(Long orderId, String orderStatus) throws OrderNotFoundException;
    List<LaundryOrderDTO> getOrderByStatus(String status);
     List<LaundryOrderDTO> getOrdersByCustomer(String username);
    List<LaundryOrderDTO> getAllOrders() throws CredentialException;
}
