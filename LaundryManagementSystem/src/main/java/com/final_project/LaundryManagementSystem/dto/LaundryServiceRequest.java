package com.final_project.LaundryManagementSystem.dto;

import lombok.Data;

@Data
public class LaundryServiceRequest {

    private Long id;
    private String serviceName;
    private String description;
    private Double basePrice;
    private Integer estimatedTimeInMinutes;
}
