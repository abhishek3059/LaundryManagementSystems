package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.model.User;

import java.time.LocalDate;
import java.util.Map;

public interface ReportService {

    Map<String, Object> generateDailyReport(LocalDate date);
    Map<String,Object> generateCustomerReport(User customer);

}
