package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.service.ReportService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@Data
@RequestMapping("api/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily")
    public ResponseEntity<Map<String, Object>> generateDailyReport(@RequestParam LocalDate date){
        Map<String,Object> report = reportService.generateDailyReport(date);
        return ResponseEntity.ok(report);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Map<String,Object>> generateCustomerReport(@PathVariable Long customerId){
       return ResponseEntity.ok(reportService.generateCustomerReport(customerId));
    }


}
