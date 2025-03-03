package com.final_project.LaundryManagementSystem.model;

import com.final_project.LaundryManagementSystem.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "username")
    @NotNull(message = "username is required")
    private String username;
    @Column(nullable = false, name = "password")
    @NotNull(message = "password is required")
    private String password;
    @NotNull(message = "at least one role must be selected")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<UserRoles> roles = new HashSet<>();
    @Column(nullable = false, name = "email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,
            message = "Enter a Valid Email ")
    @NotNull(message = "Email must be entered")
    private String email;
    @Column(name = "user_address")
    private String address;
    @Column(name = "phone_number",nullable = false)
    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$" ,
            message = "Enter a valid phone number")
    private String phoneNumber;
    @Column(name = "date_of_birth",nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

}
