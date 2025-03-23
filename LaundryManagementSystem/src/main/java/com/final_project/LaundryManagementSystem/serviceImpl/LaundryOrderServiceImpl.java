package com.final_project.LaundryManagementSystem.serviceImpl;
import com.final_project.LaundryManagementSystem.customExceptions.*;
import com.final_project.LaundryManagementSystem.dto.LaundryItemRequest;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderDTO;
import com.final_project.LaundryManagementSystem.dto.LaundryOrderRequest;
import com.final_project.LaundryManagementSystem.dto.PaymentResult;
import com.final_project.LaundryManagementSystem.enums.OrderStatus;
import com.final_project.LaundryManagementSystem.enums.SlotType;
import com.final_project.LaundryManagementSystem.enums.UserRoles;
import com.final_project.LaundryManagementSystem.model.*;
import com.final_project.LaundryManagementSystem.repo.LaundryItemRepo;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;
import com.final_project.LaundryManagementSystem.repo.LaundryServiceRepo;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import com.final_project.LaundryManagementSystem.service.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class LaundryOrderServiceImpl implements LaundryOrderService {
    private final LaundryOrderRepo ORDER_REPO;
    private final LaundryCapacityService LAUNDRY_CAPACITY_SERVICE;
    private final TimeSlotService TIME_SLOT_SERVICE;
    private final LaundryServiceRepo LAUNDRY_SERVICE_REPO;
    private final LaundryItemRepo laundryItemRepo;
    private final UserRepo USER_REPO;
    private final PaymentService paymentService;
    private final ApplicationContext applicationContext;
    private final LaundryOrderRepo laundryOrderRepo;


    @Override
    @Transactional(rollbackOn = {PaymentUnsuccessfulException.class , SlotsNotAvailableException.class} )
    public LaundryOrderDTO createOrder(LaundryOrderRequest request)
            throws SlotsNotAvailableException, PaymentUnsuccessfulException, OrderNotFoundException, OrderCapacityReachedException {

        User customer = USER_REPO.findById(request.getCustomerId())
                .orElseThrow(() -> new UserNotFoundException("Cannot locate user"));
        if(!LAUNDRY_CAPACITY_SERVICE.canAcceptNewOrdersForDate(request.getProcessingDate())){
            throw new OrderCapacityReachedException("No more orders for the day try after some time");
        }

        List<LaundryItem> items = request.getItems().stream()
                .map(this::convertLaundryItemDTOtoEntity)
                .collect(Collectors.toList());
        for(LaundryItem item : items){
            LaundryService service = item.getService();
            if(service != null && service.getId() == null){
                LAUNDRY_SERVICE_REPO.save(service);
            }
            assert item.getService() != null;
            item.setPrice(item.getService().getBasePrice() * item.getQuantity());
            laundryItemRepo.save(item);
        }
        TimeSlot pickupSlot = request.getPickupSlot();
        TimeSlot deliverySlot = request.getDeliverySlot();

        if (pickupSlot.getSlotId() == null) {
            TIME_SLOT_SERVICE.saveTimeSlot(pickupSlot); 
        }
        if (deliverySlot.getSlotId() == null) {
            TIME_SLOT_SERVICE.saveTimeSlot(deliverySlot);
        }

        LaundryOrder order = LaundryOrder.builder()
                .customer(customer)
                .orderDateTime(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .items(items)
                .pickupSlot(request.getPickupSlot())
                .deliverySlot(request.getDeliverySlot())
                .isPaid(false)
                .orderNumber(generateOrderId())
                .totalPrice(calculateTotalPriceForOrder(items))
                .specialInstructions(request.getSpecialInstruction())
                .build();

        for (LaundryItem item : items) {
            item.setOrder(order);
        }

        if (!TIME_SLOT_SERVICE.reserveSlot(request.getPickupSlot(), order, SlotType.PICKUP) ||
                !TIME_SLOT_SERVICE.reserveSlot(request.getDeliverySlot(), order, SlotType.DELIVERY)) {
            throw new SlotsNotAvailableException("Requested slots are not available");
        }
        ORDER_REPO.save(order);
        pickupSlot = TIME_SLOT_SERVICE.findSlotById(pickupSlot.getSlotId());
        deliverySlot = TIME_SLOT_SERVICE.findSlotById(deliverySlot.getSlotId());
        pickupSlot.setCapacity(pickupSlot.getCapacity() - 1);
        deliverySlot.setCapacity(deliverySlot.getCapacity() - 1);
        TIME_SLOT_SERVICE.saveTimeSlot(pickupSlot);
        TIME_SLOT_SERVICE.saveTimeSlot(deliverySlot);



        PaymentResult paymentResult = paymentService.processPayment(
                order.getId(),
                request.getPaymentMethod(),
                request.getPaymentDetails()
        );
        if(!paymentResult.isPaid()){

            throw new PaymentUnsuccessfulException("Payment could not be completed");
        }

        return convertLaundryIntoLaundryDto(order);
    }

    private String generateOrderId(){
        Random rand = new Random();
        int orderId = rand.nextInt(99999);
        return "O-" + Integer.toString(orderId);
    }
    private Double calculateTotalPriceForOrder(List<LaundryItem> items){
       return items.stream().mapToDouble(
                item -> item.getService().getBasePrice() * item.getQuantity())
                .sum();
    }

    @Override
    @Transactional
    public LaundryOrderDTO updateOrderStatus(Long orderId, String orderStatus) throws OrderNotFoundException {
        LaundryOrder order = ORDER_REPO.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order with id: "+ orderId + " does not exist enter a valid orderId"));
        OrderStatus status = OrderStatus.valueOf(orderStatus.toUpperCase());
        switch(status){
            case RECEIVED -> order.setPickupTime(LocalDateTime.now());
            case PROCESSING -> order.setProcessingStartTime(LocalDateTime.now());
            case COMPLETED -> order.setProcessingEndTime(LocalDateTime.now());
            case DELIVERED -> order.setDeliveryTime(LocalDateTime.now());
        }
        order.setOrderStatus(status);
         ORDER_REPO.save(order);
        return convertLaundryIntoLaundryDto(order);
    }

    @Override
    public List<LaundryOrderDTO> getOrderByStatus(String orderStatus) {
        OrderStatus status = OrderStatus.valueOf(orderStatus.toUpperCase());
        List<LaundryOrder> orders =  ORDER_REPO.findByOrderStatus(status);
         return orders.stream().map(this::convertLaundryIntoLaundryDto).toList();
    }

    @Override
    public List<LaundryOrderDTO> getOrdersByCustomer(String username) {
        SecurityService service = applicationContext.getBean(SecurityService.class);
        MyUserPrinciple principle = (MyUserPrinciple) service.loadUserByUsername(username);
        User customer = principle.getUser();
        List<LaundryOrder> orders =  ORDER_REPO.findByCustomerOrderByOrderDateTimeDesc(customer);
        return orders.stream().map(this::convertLaundryIntoLaundryDto).toList();
    }

    @Override
    public List<LaundryOrderDTO> getAllOrders() throws CredentialException {

        SecurityService service = applicationContext.getBean(SecurityService.class);
        MyUserPrinciple principle =(MyUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User customer = principle.getUser();
        if(customer != null && (customer.getRoles().contains(UserRoles.ROLE_ADMIN) || customer.getRoles().contains(UserRoles.ROLE_STAFF))) {
            List<LaundryOrder> orders = laundryOrderRepo.findAll();
            return orders.stream().map(this::convertLaundryIntoLaundryDto).toList();
        }
        else throw new CredentialException("Access denied");
    }

    private LaundryItem convertLaundryItemDTOtoEntity(LaundryItemRequest dto) {
        return LaundryItem.builder()
                .service(LAUNDRY_SERVICE_REPO.findById(dto.getServiceId()).orElse(null))
                .quantity(dto.getQuantity())
                .itemDescription(dto.getItemDescription())
                .color(dto.getColor())
                .fabric(dto.getFabric())
                .specialNotes(dto.getSpecialNotes())
                .price(dto.getPrice())
                .build();
    }

    private LaundryOrderDTO convertLaundryIntoLaundryDto(LaundryOrder order) {
        return LaundryOrderDTO.builder()
                .id(order.getId())
                .items(order.getItems().stream().map(this::convertLaundryItemToDTO).toList())
                .orderDateTime(order.getOrderDateTime())
                .deliverySlot(order.getDeliverySlot())
                .deliveryTime(order.getDeliveryTime())
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(order.getOrderStatus())
                .pickupTime(order.getPickupTime())
                .specialInstructions(order.getSpecialInstructions())
                .pickupSlot(order.getPickupSlot())
                .orderNumber(order.getOrderNumber())
                .totalPrice(order.getTotalPrice())
                .processingEndTime(order.getProcessingEndTime())
                .processingStartTime(order.getProcessingStartTime())
                .customer(order.getCustomer())
                .isPaid(order.isPaid())
                .build();
    }
    private LaundryItemRequest convertLaundryItemToDTO(LaundryItem item) {
        return LaundryItemRequest.builder()
                .serviceId(item.getItemId())
                .itemDescription(item.getItemDescription())
                .color(item.getColor())
                .fabric(item.getFabric())
                .specialNotes(item.getSpecialNotes())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }

}
