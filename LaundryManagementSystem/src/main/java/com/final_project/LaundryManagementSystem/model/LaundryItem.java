package com.final_project.LaundryManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class LaundryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @ManyToOne
    private LaundryOrder order;

    @ManyToOne
    private LaundryService service;

    private String itemDescription;
    private Integer quantity;
    private Double price;

    private String color;
    private String fabric;
    private String specialNotes;

}
