package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.customExceptions.UserAlreadyExistsException;
import com.final_project.LaundryManagementSystem.dto.UserRegistrationRequest;
import com.final_project.LaundryManagementSystem.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("api")
public class AdminController {

    private final UserService service;
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-admin")
    public ResponseEntity<?> addAdminUser(@RequestBody UserRegistrationRequest request)
    throws UserAlreadyExistsException {
        boolean result = service.registerAsAdmin(request);
        if(result){
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("(hasAnyRole('ADMIN','STAFF')")
    @PostMapping("/staff/create-staff")
    public ResponseEntity<?> addStaffUser(@RequestBody UserRegistrationRequest request)
            throws  UserAlreadyExistsException{
        boolean result = service.registerAsStaff(request);
        if(result){
            return ResponseEntity.ok().build();
        }else return ResponseEntity.badRequest().build();
    }
}
