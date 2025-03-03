package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.repo.TimeSlotRepo;
import com.final_project.LaundryManagementSystem.service.TimeSlotService;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Builder
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepo timeSlotRepo;


    @Override
    public List<TimeSlot> getAvailableSlots(LocalDate day, SlotType type) {
        List<TimeSlot> slots = timeSlotRepo.findByDateAndType(day,type);
       return slots.stream().filter(TimeSlot::isAvailable).toList();
    }

    @Override
    public boolean reserveSlot(TimeSlot slot, LaundryOrder order, SlotType type) {
        if(!slot.isAvailable()){
            return false;
        }
        if(type == SlotType.PICKUP){
            order.setPickupSlot(slot);
        }
        else{
            order.setDeliverySlot(slot);
        }
        return true;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void generateSlotsForUpcomingPeriod() {
        LocalDate startDate =  LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(14);

        for(LocalDate date = startDate; !date.isAfter(endDate) ; date = date.plusDays(1))
            createSlotForDate(startDate);

    }

    @Transactional
    private void createSlotForDate(LocalDate date) {
        if(timeSlotRepo.existsByDate(date)) return;

        LocalTime startTime = LocalTime.of(8,0);
        while(startTime.isBefore(LocalTime.of(18,0))){
            TimeSlot pickupSlot = TimeSlot.builder()
                    .date(date)
                    .startTime(startTime)
                    .endTime(startTime.plusMinutes(60))
                    .type(SlotType.PICKUP)
                    .capacity(3)
                    .build();
            timeSlotRepo.save(pickupSlot);
            TimeSlot deliverySlot = TimeSlot.builder()
                    .date(date.plusDays(2))
                    .startTime(startTime)
                    .endTime(startTime.plusMinutes(60))
                    .type(SlotType.DELIVERY)
                    .capacity(3).build();
            timeSlotRepo.save(deliverySlot);
        }


    }
}
