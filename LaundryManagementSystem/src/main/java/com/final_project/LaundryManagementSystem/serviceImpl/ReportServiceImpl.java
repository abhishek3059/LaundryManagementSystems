package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;
import com.final_project.LaundryManagementSystem.service.ReportService;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Data
public class ReportServiceImpl implements ReportService {

    private final LaundryOrderRepo orderRepo;
    @Override
    public Map<String, Object> generateDailyReport(LocalDate date) {
        Map<String, Object> report = new HashMap<>();
        report.put("totalOrders", orderRepo.countByProcessingDate(date));
        report.put("totalRevenue",orderRepo.sumRevenueByProcessingDate(date));
        report.put("serviceBreakdown",orderRepo.countByServiceType(date));
        report.put("peakHours",orderRepo.findPeakHoursByDate(date));
        return report;
    }

    @Override
    public Map<String, Object> generateCustomerReport(User customer) {
        Map<String, Object> report = new HashMap<>();
        report.put("totalOrders",orderRepo.countByCustomer(customer));
        report.put("totalSpent",orderRepo.sumRevenueByCustomer(customer));
        report.put("favouriteServices",orderRepo.findFavouriteServicesByCustomer(customer));
        return report;
    }
}
