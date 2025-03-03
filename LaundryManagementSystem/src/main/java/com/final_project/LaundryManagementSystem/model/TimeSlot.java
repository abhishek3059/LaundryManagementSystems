package com.final_project.LaundryManagementSystem.model;

import com.final_project.LaundryManagementSystem.enums.SlotType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long slotId;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private SlotType type;

    private Integer capacity;

    @OneToMany(mappedBy = "pickupSlot")
    private List<LaundryOrder> pickupOrders;

    @OneToMany(mappedBy = "deliverySlot")
    private List<LaundryOrder> deliveryOrders;

    public boolean isAvailable(){
        if(type == SlotType.PICKUP){
            return pickupOrders.size() < capacity;
        }
        else{
            return deliveryOrders.size() < capacity;
        }
    }
}
