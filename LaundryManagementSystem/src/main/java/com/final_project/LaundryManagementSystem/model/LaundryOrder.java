package com.final_project.LaundryManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore  // Prevents recursion with User
    private User customer;

    private String orderNumber;
    private LocalDateTime orderDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore  // Prevents deep nesting with LaundryItem
    private List<LaundryItem> items;

    @ManyToOne
    @JsonIgnore // Prevents recursion with TimeSlot
    private TimeSlot pickupSlot;

    @ManyToOne
    @JsonIgnore // Prevents recursion with TimeSlot
    private TimeSlot deliverySlot;

    private boolean isPaid;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Double totalPrice;
    private LocalDateTime pickupTime;
    private LocalDateTime processingStartTime;
    private LocalDateTime processingEndTime;
    private LocalDateTime deliveryTime;
    private String specialInstructions;
}
