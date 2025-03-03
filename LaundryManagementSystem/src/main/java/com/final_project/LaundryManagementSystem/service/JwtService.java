package com.final_project.LaundryManagementSystem.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);
    String extractUserName(String token);
    boolean validateToken(String token, UserDetails userDetails);
    boolean validateToken(String token);
}
