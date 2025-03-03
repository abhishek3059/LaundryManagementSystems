package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.customExceptions.OrderNotFoundException;
import com.final_project.LaundryManagementSystem.dto.PaymentResult;
import com.final_project.LaundryManagementSystem.enums.PaymentMethod;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.repo.LaundryOrderRepo;
import com.final_project.LaundryManagementSystem.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class PaymentServiceImpl implements PaymentService {

    private final LaundryOrderRepo orderRepo;
    @Override
    @Transactional
    public PaymentResult processPayment(String orderId, PaymentMethod paymentMethod, String paymentDetails) throws OrderNotFoundException {
        LaundryOrder order = orderRepo.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order with id: "+ orderId+" does not exists"));
        boolean paymentResult = processPaymentWithGateway(order.getTotalPrice(),paymentMethod,paymentDetails);
        if(paymentResult){
            order.setPaid(true);
            order.setPaymentMethod(paymentMethod);
            orderRepo.save(order);
            return new PaymentResult(true,"success");
        }
        return new PaymentResult(false,"failed");
    }

    private boolean processPaymentWithGateway(Double totalPrice, PaymentMethod paymentMethod, String paymentDetails) {
        double rand = Math.random();
        return rand > 0.2;
    }
}
