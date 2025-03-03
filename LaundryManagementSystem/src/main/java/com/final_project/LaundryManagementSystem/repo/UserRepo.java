package com.final_project.LaundryManagementSystem.repo;

import com.final_project.LaundryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
