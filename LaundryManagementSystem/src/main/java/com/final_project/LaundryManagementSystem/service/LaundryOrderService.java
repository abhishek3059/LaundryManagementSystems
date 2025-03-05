package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.customExceptions.OrderCapacityReachedException;
import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.customExceptions.PaymentUnsuccessfulException;
import com.final_project.LaundryManagementSystem.customExceptions.SlotsNotAvailableException;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderDTO;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderRequest;
import com.final_project.LaundryManagementSystem.model.User;

import java.util.List;

public interface LaundryOrderService {
    public LaundryOrderDTO createOrder(LaundryOrderRequest laundryOrderRequest) throws OrderNotFoundException, SlotsNotAvailableException, PaymentUnsuccessfulException, OrderCapacityReachedException;
    public LaundryOrderDTO updateOrderStatus(Long orderId, String orderStatus) throws OrderNotFoundException;
    public List<LaundryOrderDTO> getOrderByStatus(String status);
    public List<LaundryOrderDTO> getOrdersByCustomer(User customer);


}
