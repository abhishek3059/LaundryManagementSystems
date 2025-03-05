package com.final_project.LaundryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaundryItemRequest {
    private Long serviceId;  // ID of the service selected for the item
    private String itemDescription;  // Description of the item
    private Integer quantity;  // Number of items
    private String color;  // Color of the fabric
    private String fabric;  // Fabric type
    private String specialNotes;  // Any special notes for the order
    private Double price;
}
