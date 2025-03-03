package com.final_project.LaundryManagementSystem.repo;

import com.final_project.LaundryManagementSystem.model.LaundryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaundryServiceRepo extends JpaRepository<LaundryService , String> {
}
