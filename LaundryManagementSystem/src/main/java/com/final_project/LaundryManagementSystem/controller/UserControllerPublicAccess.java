package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.customExceptions.UnauthorizedAccessException;
import com.final_project.LaundryManagementSystem.customExceptions.UserAlreadyExistsException;
import com.final_project.LaundryManagementSystem.dto.UserLoginRequest;
import com.final_project.LaundryManagementSystem.dto.UserRegistrationRequest;
import com.final_project.LaundryManagementSystem.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("api")
public class UserControllerPublicAccess {

    private final UserService service;


    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationRequest request)
            throws UserAlreadyExistsException, UnauthorizedAccessException {
        boolean result =  service.registerUser(request);
        if(result){
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.badRequest().build();
    }
    @GetMapping("/auth/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> login (@RequestBody UserLoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }
    @GetMapping("/auth/validate/{token}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> validateToken(@PathVariable String token){
        boolean result =  service.validateToken(token);
        if(result){
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.notFound().build();
    }




}
