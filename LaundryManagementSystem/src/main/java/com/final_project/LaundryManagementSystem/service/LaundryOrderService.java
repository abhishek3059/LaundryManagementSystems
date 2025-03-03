package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.enums.OrderStatus;
import com.final_project.LaundryManagementSystem.model.LaundryItem;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;

import java.util.List;

public interface LaundryOrderService {
    public LaundryOrder createOrder(User customer,
                                    List<LaundryItem> items,
                                    TimeSlot pickupSlot,
                                    TimeSlot deliverySlot,
                                    String specialInstruction) throws OrderNotFoundException;
    public LaundryOrder updateOrderStatus(String orderId, OrderStatus orderStatus) throws OrderNotFoundException;
    public List<LaundryOrder> getOrderByStatus(OrderStatus status);
    public List<LaundryOrder> getOrdersByCustomer(User customer);


}
