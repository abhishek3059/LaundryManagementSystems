package com.final_project.LaundryManagementSystem.config;

import com.final_project.LaundryManagementSystem.enums.UserRoles;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import com.final_project.LaundryManagementSystem.service.TimeSlotService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Data
public class AdminInitializer {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final TimeSlotService timeSlotService;
    @PostConstruct
    public void init() {
        if (!userRepo.existsByUsername("admin")) {
            Set<UserRoles> roles = new HashSet<>();
            roles.add(UserRoles.ROLE_ADMIN);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            User admin = User.builder()
                    .username("admin")
                    .password(encoder.encode("admin"))
                    .email("admin@example.com")
                    .phoneNumber("7767034897")
                    .address("Admin Address")
                    .roles(roles)
                    .dateOfBirth(LocalDate.parse("02-04-2003", formatter))
                    .build();
            userRepo.save(admin);
            timeSlotService.generateSlotsForUpcomingPeriod();
        }
    }
}




