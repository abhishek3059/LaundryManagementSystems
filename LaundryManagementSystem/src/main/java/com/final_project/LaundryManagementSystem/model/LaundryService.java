package com.final_project.LaundryManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LaundryService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String serviceName;
    private String description;
    private Double basePrice;
    private Integer estimatedTimeInMinutes;


}
