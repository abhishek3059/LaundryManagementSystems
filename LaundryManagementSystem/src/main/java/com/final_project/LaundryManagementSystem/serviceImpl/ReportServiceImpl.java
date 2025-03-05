package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.customExceptions.UserNotFoundException;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import com.final_project.LaundryManagementSystem.service.ReportService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Data
@Service
public class ReportServiceImpl implements ReportService {

    private final LaundryOrderRepo orderRepo;
    private final UserRepo userRepo;
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
    public Map<String, Object> generateCustomerReport(Long customerId) {
        User customer = userRepo.findById(customerId).orElseThrow(
                ()-> new UserNotFoundException("User with customerId "+ customerId + " does not exists"));

        Map<String, Object> report = new HashMap<>();
        report.put("totalOrders",orderRepo.countByCustomer(customer));
        report.put("totalSpent",orderRepo.sumRevenueByCustomer(customer));
        report.put("favouriteServices",orderRepo.findFavouriteServicesByCustomer(customer));
        return report;
    }
}
