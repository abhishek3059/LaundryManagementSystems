package com.final_project.LaundryManagementSystem.controller;

import com.final_project.LaundryManagementSystem.customExceptions.OrderCapacityReachedException;
import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.customExceptions.PaymentUnsuccessfulException;
import com.final_project.LaundryManagementSystem.customExceptions.SlotsNotAvailableException;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderDTO;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderRequest;
import com.final_project.LaundryManagementSystem.dto.PaymentResult;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.service.*;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@Data
public class LaundryOrderController {
    private final LaundryOrderService laundryOrderService;
    private final LaundryCapacityService laundryCapacityService;
    private final TimeSlotService timeSlotService;
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<LaundryOrderDTO> createOrder(@RequestBody LaundryOrderRequest request)
            throws OrderCapacityReachedException, SlotsNotAvailableException, OrderNotFoundException, PaymentUnsuccessfulException {
            LaundryOrderDTO order = laundryOrderService.createOrder(request);
//          notificationService.sendOrderStatusUpdate(order);
            return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF)")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<LaundryOrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam("status") String status)
            throws OrderNotFoundException {
            LaundryOrderDTO order = laundryOrderService.updateOrderStatus(orderId,status);
//          notificationService.sendOrderStatusUpdate(order);
            return ResponseEntity.ok(order);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LaundryOrderDTO>> getOrdersByOrderStatus(@PathVariable("status") String orderStatus){
        List<LaundryOrderDTO> orders = laundryOrderService.getOrderByStatus(orderStatus);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/customer/{username}")
    public ResponseEntity<List<LaundryOrderDTO>> getOrdersByCustomer(@PathVariable String username){
        List<LaundryOrderDTO> orders = laundryOrderService.getOrdersByCustomer(username);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("get-orders")
    public ResponseEntity<List<LaundryOrderDTO>> getOrders() throws CredentialException {
       return ResponseEntity.ok(laundryOrderService.getAllOrders());
    }




}
