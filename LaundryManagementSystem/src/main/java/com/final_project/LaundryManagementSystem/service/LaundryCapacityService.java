package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface LaundryCapacityService {
  boolean canAcceptNewOrdersForDate(LocalDate processingDate);
  LocalDate setCompletionDate(LaundryOrder order);


}
