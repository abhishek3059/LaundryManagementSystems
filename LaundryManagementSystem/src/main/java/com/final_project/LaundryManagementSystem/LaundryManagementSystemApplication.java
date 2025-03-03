package com.final_project.LaundryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LaundryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaundryManagementSystemApplication.class, args);
	}

}
