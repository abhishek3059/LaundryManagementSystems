package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.dto.LaundryServiceRequest;
import com.final_project.LaundryManagementSystem.model.LaundryService;
import com.final_project.LaundryManagementSystem.service.LaundryServices;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("api/service")
public class LaundryServicesController {

    private final LaundryServices laundryServices;

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PostMapping("/save")
    public ResponseEntity<Void> saveService(@RequestBody LaundryServiceRequest request){
        boolean result = laundryServices.saveService(request);
        if(result) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<LaundryService>> getAllServices(){
        return ResponseEntity.ok(laundryServices.getAllServices());
    }

}
