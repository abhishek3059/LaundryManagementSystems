package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.customExceptions.UnauthorizedAccessException;
import com.final_project.LaundryManagementSystem.customExceptions.UserAlreadyExistsException;
import com.final_project.LaundryManagementSystem.dto.UserLoginRequest;
import com.final_project.LaundryManagementSystem.dto.UserRegistrationRequest;
import com.final_project.LaundryManagementSystem.service.UserService;
import lombok.Data;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PostMapping("/auth/login")
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

    @GetMapping("/auth/getCustomerId")
    public ResponseEntity<Long> getCustomerId(@RequestParam String token){
        Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        System.out.println(auth);
        return null;
    }




}
