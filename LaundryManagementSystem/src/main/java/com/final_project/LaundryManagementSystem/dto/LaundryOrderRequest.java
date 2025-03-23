package com.final_project.LaundryManagementSystem.dto;

import com.final_project.LaundryManagementSystem.enums.PaymentMethod;
import com.final_project.LaundryManagementSystem.model.LaundryItem;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class LaundryOrderRequest {

    private Long customerId;
    private List<LaundryItemRequest> items;
    private TimeSlot pickupSlot;
    private TimeSlot deliverySlot;
    private String specialInstruction;
    private LocalDate processingDate;
    private PaymentMethod paymentMethod;
    private String paymentDetails;
}
