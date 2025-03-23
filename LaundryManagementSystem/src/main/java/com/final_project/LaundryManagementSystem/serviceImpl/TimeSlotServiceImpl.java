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
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;

    @Service
    @Data
    @AllArgsConstructor
    @Builder
    public class TimeSlotServiceImpl implements TimeSlotService {

        private final TimeSlotRepo timeSlotRepo;


        @Override
        public List<TimeSlot> getAvailableSlots(String day, String type) {
            LocalDate parsedDate = LocalDate.parse(day, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            SlotType slotType = SlotType.valueOf(type.toUpperCase());
            List<TimeSlot> slots = timeSlotRepo.findByDateAndType(parsedDate,slotType);
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
//        @Scheduled(cron = "0 0 0 * * *")
        public void generateSlotsForUpcomingPeriod() {
            LocalDate startDate = LocalDate.now().plusDays(1); // Start from tomorrow
            LocalDate endDate = startDate.plusDays(2);        // Generate slots for the next 2 days

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                createSlotForDate(date);
            }
        }

        @Override
        public void saveTimeSlot(TimeSlot pickupSlot) {
            timeSlotRepo.save(pickupSlot);
        }

        @Override
        public TimeSlot findSlotById(long timeSlotId) {
            return timeSlotRepo.findById(timeSlotId).orElseThrow(()-> new RuntimeException("Given time slot cannot be fetched"));
        }

        @Transactional
        private void createSlotForDate(LocalDate date) {
            if (timeSlotRepo.existsByDate(date)) {
                System.out.println("Slots already exist for date: " + date);
                return;
            }

            List<TimeSlot> slots = new ArrayList<>();
            LocalTime startTime = LocalTime.of(8, 0); // Start at 8:00 AM
            while (startTime.isBefore(LocalTime.of(18, 0))) { // End at 6:00 PM
                // Create pickup slot
                TimeSlot pickupSlot = TimeSlot.builder()
                        .date(date)
                        .startTime(startTime)
                        .endTime(startTime.plusMinutes(60))
                        .type(SlotType.PICKUP)
                        .capacity(3)
                        .build();
                slots.add(pickupSlot);

                // Create delivery slot (2 days after the pickup date)
                LocalDate deliveryDate = date.plusDays(2);
                TimeSlot deliverySlot = TimeSlot.builder()
                        .date(deliveryDate)
                        .startTime(startTime)
                        .endTime(startTime.plusMinutes(60))
                        .type(SlotType.DELIVERY)
                        .capacity(3)
                        .build();
                slots.add(deliverySlot);

                startTime = startTime.plusMinutes(60); // Increment by 1 hour
            }

            // Save all slots in a single batch
            timeSlotRepo.saveAll(slots);
            System.out.println("Created " + slots.size() + " slots for date: " + date);
        }
    }
