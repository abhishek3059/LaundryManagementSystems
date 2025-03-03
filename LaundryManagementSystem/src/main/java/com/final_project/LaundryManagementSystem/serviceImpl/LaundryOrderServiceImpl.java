package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.enums.OrderStatus;
import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.model.LaundryItem;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.TimeSlot;
import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;
import com.final_project.LaundryManagementSystem.service.LaundryCapacityService;
import com.final_project.LaundryManagementSystem.service.LaundryOrderService;
import com.final_project.LaundryManagementSystem.service.TimeSlotService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class LaundryOrderServiceImpl implements LaundryOrderService {
    private final LaundryOrderRepo ORDER_REPO;
    private final LaundryCapacityService LAUNDRY_CAPACITY_SERVICE;
    private final TimeSlotService TIME_SLOT_SERVICE;

    @Override
    @Transactional
    public LaundryOrder createOrder(User customer,
                                    List<LaundryItem> items,
                                    TimeSlot pickupSlot, TimeSlot deliverySlot,
                                    String specialInstruction)
                                    throws OrderNotFoundException {
        LaundryOrder order = LaundryOrder.builder()
                .customer(customer)
                .orderDateTime(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .items(items)
                .pickupSlot(pickupSlot)
                .deliverySlot(deliverySlot)
                .isPaid(false)
                .totalPrice(calculateTotalPriceForOrder(items))
                .specialInstructions(specialInstruction)
                .build();
        if((!TIME_SLOT_SERVICE.reserveSlot(pickupSlot , order, SlotType.PICKUP)) ||
           (!TIME_SLOT_SERVICE.reserveSlot(deliverySlot,order,SlotType.DELIVERY)))
            throw new OrderNotFoundException("Selected Time for Delivery or Pickup are not available");

        return ORDER_REPO.save(order);
                
    }
    private Double calculateTotalPriceForOrder(List<LaundryItem> items){
       return items.stream().mapToDouble(
                item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    @Transactional
    public LaundryOrder updateOrderStatus(String orderId, OrderStatus orderStatus) throws OrderNotFoundException {
        LaundryOrder order = ORDER_REPO.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order with id: "+ orderId + " does not exist enter a valid orderId"));
        switch(orderStatus){
            case RECEIVED -> order.setPickupTime(LocalDateTime.now());
            case PROCESSING -> order.setProcessingStartTime(LocalDateTime.now());
            case COMPLETED -> order.setProcessingEndTime(LocalDateTime.now());
            case DELIVERED -> order.setDeliveryTime(LocalDateTime.now());
        }
        return ORDER_REPO.save(order);
    }

    @Override
    public List<LaundryOrder> getOrderByStatus(OrderStatus orderStatus) {
        return ORDER_REPO.findByOrderStatus(orderStatus);
    }

    @Override
    public List<LaundryOrder> getOrdersByCustomer(User customer) {
        return ORDER_REPO.findByCustomerOrderByOrderDateTimeDesc(customer);
    }
}
