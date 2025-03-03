package com.final_project.LaundryManagementSystem.repo;

import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepo extends JpaRepository<TimeSlot,String> {
    List<TimeSlot> findByDateAndType(LocalDate date, SlotType type);
    boolean existsByDate(LocalDate date);

}
