package com.final_project.LaundryManagementSystem.service;

import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotService {
    public List<TimeSlot> getAvailableSlots(String day, String type);
    public boolean reserveSlot(TimeSlot slot, LaundryOrder order, SlotType type);
    public void generateSlotsForUpcomingPeriod();

    void saveTimeSlot(TimeSlot pickupSlot);

    TimeSlot findSlotById(long timeSlotId);
}
