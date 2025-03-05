package com.final_project.LaundryManagementSystem.serviceImpl;
import com.final_project.LaundryManagementSystem.customExceptions.UnauthorizedAccessException;
import com.final_project.LaundryManagementSystem.customExceptions.UserAlreadyExistsException;
import com.final_project.LaundryManagementSystem.dto.UserLoginRequest;
import com.final_project.LaundryManagementSystem.dto.UserRegistrationRequest;
import com.final_project.LaundryManagementSystem.enums.UserRoles;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import com.final_project.LaundryManagementSystem.service.JwtService;
import com.final_project.LaundryManagementSystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;


@Data
@Service
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    @Transactional
    public boolean registerUser(UserRegistrationRequest request) throws UserAlreadyExistsException, UnauthorizedAccessException {
        if(userRepo.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("User Already Exists by the username "+ request.getUsername()+ " kindly please login");
        }

        LocalDate dateOfBirth;
        try{
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            dateOfBirth = LocalDate.parse(request.getDateOfBirth(),dateTimeFormatter);
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format , Please use dd-MM-yyyy");
        }

        // Create and initialize a Set for roles before building the user
        Set<UserRoles> userRoles = new HashSet<>();
        userRoles.add(UserRoles.ROLE_USER);  // Add default role

        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .dateOfBirth(dateOfBirth)
                .roles(userRoles)  // Set the pre-initialized roles
                .build();

        // Handle any additional roles from the request if needed
        if(request.getRoles() != null) {
            for(String roleName : request.getRoles()) {
                try {
                    UserRoles role = UserRoles.valueOf(roleName);
                    user.getRoles().add(role);
                } catch(IllegalArgumentException e) {
                    // Log invalid role and continue
                    System.out.println("Invalid role: " + roleName);
                }
            }
        }

        userRepo.save(user);
        return true;
    }

    @Override
    public String login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(request.getUsername());
        }
        else throw new BadCredentialsException("Token is not valid ");
    }

    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    @Override
    @Transactional
    public boolean registerAsAdmin(UserRegistrationRequest request) throws UserAlreadyExistsException, IllegalArgumentException {
        // Check if the username already exists
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("User Already Exists with username: " + request.getUsername() + ". Kindly please login.");
        }

        // Validate roles
        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Roles are required.");
        }

        // Ensure the request contains the ROLE_ADMIN role
        if (!request.getRoles().contains("ROLE_ADMIN")) {
            throw new IllegalArgumentException("Admin users must have the ROLE_ADMIN role.");
        }
        Set<UserRoles> roles = new HashSet<>();
        roles.add(UserRoles.ROLE_USER); //default
        // Build the User object

        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword())) // Encode the password
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth(),DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .roles(roles)
                .build();


        // Add roles from the request
        for (String role : request.getRoles()) {
            user.getRoles().add(UserRoles.valueOf(role.startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase()));
        }

        // Save the user
        userRepo.save(user);
        return true;
    }
    @Override
    public boolean registerAsStaff(UserRegistrationRequest request)
            throws UserAlreadyExistsException{

        if(userRepo.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("User with username " + request.getUsername() + " already exists");
        }
        if(request.getRoles() == null || request.getRoles().isEmpty()){
            throw new IllegalArgumentException("Roles cannot be empty");
        }
        if(!request.getRoles().contains("ROLE_STAFF")){
            throw new IllegalArgumentException("To be registered as staff the role must contain STAFF role");
        }

        Set<UserRoles> roles = new HashSet<>();
        roles.add(UserRoles.ROLE_USER); //default
        // Build the User object

        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword())) // Encode the password
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth(),DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .roles(roles)
                .build();


        // Add roles from the request
        for (String role : request.getRoles()) {
            user.getRoles().add(UserRoles.valueOf(role.startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase()));
        }

        // Save the user
        userRepo.save(user);
        return true;


    }

}
