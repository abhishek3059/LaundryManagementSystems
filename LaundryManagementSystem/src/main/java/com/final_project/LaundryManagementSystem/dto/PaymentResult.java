package com.final_project.LaundryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PaymentResult {
    private boolean isPaid;
    private String message;
    private String transactionId;
    private LocalDateTime timeStamp;

    public PaymentResult (boolean isPaid, String message){
        this.isPaid = isPaid;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

}
