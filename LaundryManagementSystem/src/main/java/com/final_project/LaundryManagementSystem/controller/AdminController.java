package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.customExceptions.UserAlreadyExistsException;
import com.final_project.LaundryManagementSystem.dto.UserRegistrationRequest;
import com.final_project.LaundryManagementSystem.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
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
}
