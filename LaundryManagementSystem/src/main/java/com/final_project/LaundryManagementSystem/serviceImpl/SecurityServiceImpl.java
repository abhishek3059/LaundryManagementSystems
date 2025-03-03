package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.model.MyUserPrinciple;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.service.SecurityService;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user with "+username+" does not exist"));
        return new MyUserPrinciple(user);
    }
}
