package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.service.TimeSlotService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/timeslots")
@Data
public class TimeSlotController {

    private final TimeSlotService timeSlotService;
    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAvailableSlots(@RequestParam String day, @RequestParam String type){
        List<TimeSlot> slots = timeSlotService.getAvailableSlots(day, type);
        return ResponseEntity.ok(slots);
    }
    @PostMapping("/get-slot")
    public ResponseEntity<TimeSlot> getTimeSlot(@RequestParam("timeSlotId") long timeSlotId)
    {
        return ResponseEntity.ok(timeSlotService.findSlotById(timeSlotId));
    }
    @PostMapping("/generate")
    public void generateTimeSlot(){
        timeSlotService.generateSlotsForUpcomingPeriod();
    }


}
