package com.final_project.LaundryManagementSystem.service;

import java.time.LocalDate;
import java.util.Map;

public interface ReportService {

    Map<String, Object> generateDailyReport(LocalDate date);
    Map<String,Object> generateCustomerReport(Long customerId);

}
