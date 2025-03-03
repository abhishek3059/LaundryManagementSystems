package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import com.final_project.LaundryManagementSystem.service.NotificationService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserRepo userRepo;
    @Override
    public void sendOrderStatusUpdate(LaundryOrder order) {
        User customer = order.getCustomer();
        String message = generateStatusUpdateMessage(order);

    }

    @Override
    public String generateStatusUpdateMessage(LaundryOrder order) {
        return switch (order.getOrderStatus()) {
            case PENDING -> "Your order #" + order.getOrderNumber() +
                    " has been placed. Pickup scheduled for " +
                    formatDateTime(order.getPickupSlot());
            case RECEIVED -> "Your laundry has been picked up and received by our facility.";
            case PROCESSING -> "Your laundry is now being processed.";
            case COMPLETED -> "Your laundry is ready for delivery on " +
                    formatDateTime(order.getDeliverySlot());
            case DELIVERED -> "Your laundry has been delivered. Thank you for using our service!";
            default -> "Update on your order #" + order.getOrderNumber();
        };
    }
    private String formatDateTime(TimeSlot slot){
        return slot.getDate().toString() +
        " between " + slot.getStartTime() +
        " and " + slot.getEndTime();
    }

    private void  sendEmail(String email,String subject, String message){

    }
    private void sendSMS(String phone, String message){

    }
}
