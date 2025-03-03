package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.model.LaundryItem;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;
import com.final_project.LaundryManagementSystem.service.LaundryCapacityService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@RequiredArgsConstructor
@Data
public class LaundryCapacityServiceImpl implements LaundryCapacityService {

    private final LaundryOrderRepo laundryOrderRepo;
    private final Integer DAILY_CAPACITY = 500;
    @Override
    public boolean canAcceptNewOrdersForDate(LocalDate processingDate) {
        Integer totalItemForDate = laundryOrderRepo.countTotalItemsForProcessingDate(processingDate);
        return totalItemForDate < DAILY_CAPACITY;
    }

    @Override
    public LocalDate setCompletionDate(LaundryOrder order) {
        int totalItems = order.getItems().stream()
                .mapToInt(LaundryItem::getQuantity).sum();
        int totalProcessingMinutes = order.getItems().stream()
                .mapToInt(item -> item.getQuantity() * item.getService().getEstimatedTimeInMinutes())
                .sum();
        int processingDays = (int) Math.ceil(totalProcessingMinutes / (8.0 * 60));

        LocalDate pickupDate = order.getPickupSlot().getDate();
        return pickupDate.plusDays(processingDays);
    }
}
