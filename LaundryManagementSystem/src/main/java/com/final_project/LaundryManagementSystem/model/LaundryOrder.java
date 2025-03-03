package com.final_project.LaundryManagementSystem.model;

import com.final_project.LaundryManagementSystem.enums.OrderStatus;
import com.final_project.LaundryManagementSystem.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class LaundryOrder {
    @Id @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    private String orderNumber;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order" , cascade =  CascadeType.ALL)
    private List<LaundryItem> items;

    @ManyToOne
    private TimeSlot pickupSlot;

    @ManyToOne
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
