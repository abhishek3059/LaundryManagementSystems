package com.final_project.LaundryManagementSystem.dto;

import com.final_project.LaundryManagementSystem.enums.OrderStatus;
import com.final_project.LaundryManagementSystem.enums.PaymentMethod;
import com.final_project.LaundryManagementSystem.model.LaundryItem;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class LaundryOrderDTO {
    private Long id;
    private User customer;
    private String orderNumber;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private List<LaundryItemRequest> items;
    private TimeSlot pickupSlot;
    private TimeSlot deliverySlot;
    private boolean isPaid;
    private PaymentMethod paymentMethod;
    private Double totalPrice;
    private LocalDateTime pickupTime;
    private LocalDateTime processingStartTime;
    private LocalDateTime processingEndTime;
    private LocalDateTime deliveryTime;
    private String specialInstructions;
}
