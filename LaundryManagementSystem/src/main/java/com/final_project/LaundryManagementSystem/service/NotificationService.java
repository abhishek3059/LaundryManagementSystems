package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.model.LaundryOrder;

public interface NotificationService {
    public void sendOrderStatusUpdate(LaundryOrder order);
    public String generateStatusUpdateMessage(LaundryOrder order);
    
}
