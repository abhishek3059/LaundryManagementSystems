package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.dto.LaundryServiceRequest;
import com.final_project.LaundryManagementSystem.model.LaundryService;

import java.util.List;

public interface LaundryServices {

    boolean saveService(LaundryServiceRequest request);
    List<LaundryService> getAllServices();
}
