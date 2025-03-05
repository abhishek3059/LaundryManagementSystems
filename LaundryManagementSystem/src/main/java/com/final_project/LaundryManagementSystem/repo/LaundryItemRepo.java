package com.final_project.LaundryManagementSystem.repo;

import com.final_project.LaundryManagementSystem.model.LaundryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaundryItemRepo extends JpaRepository<LaundryItem, Long> {
}
