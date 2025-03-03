package com.final_project.LaundryManagementSystem.repo;

import com.final_project.LaundryManagementSystem.enums.OrderStatus;
import com.final_project.LaundryManagementSystem.model.LaundryOrder;
import com.final_project.LaundryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LaundryOrderRepo extends JpaRepository<LaundryOrder,String> {
    @Query(
            "SELECT SUM(li.quantity) FROM LaundryOrder lo JOIN lo.items li " +
                    "WHERE lo.orderStatus = com.final_project.LaundryManagementSystem.enums.OrderStatus.PROCESSING AND " +
                    "CAST(lo.processingStartTime AS date) = :processingDate"
    )
    Integer countTotalItemsForProcessingDate(@Param("processingDate") LocalDate processingDate);

    List<LaundryOrder> findByOrderStatus(OrderStatus orderStatus);

    List<LaundryOrder> findByCustomerOrderByOrderDateTimeDesc(User customer);
    @Query("SELECT COUNT(o) FROM LaundryOrder o " +
            "WHERE DATE(o.processingStartTime) = :date")
    Long countByProcessingDate(@Param("date") LocalDate date);
    @Query("SELECT SUM(o.totalPrice) FROM LaundryOrder o " +
            "WHERE DATE(o.processingStartTime) = :date")
    Double sumRevenueByProcessingDate(@Param("date") LocalDate date);
    @Query("SELECT s.serviceName, COUNT(li) FROM LaundryOrder o " +
            "JOIN o.items li JOIN li.service s " +
            "WHERE CAST(o.processingStartTime AS date) = :date GROUP BY s.serviceName")
    List<Object[]> countByServiceType(LocalDate date);
    @Query("SELECT FUNCTION('HOUR', o.orderDateTime) as hour, COUNT(o) as count " +
            "FROM LaundryOrder o " +
            "WHERE DATE(o.orderDateTime) = :date " +
            "GROUP BY FUNCTION('HOUR', o.orderDateTime) " +
            "ORDER BY count DESC")
    List<Object[]> findPeakHoursByDate(@Param("date") LocalDate date);
    @Query("SELECT COUNT(o) FROM LaundryOrder o WHERE o.customer = :customer")
    Long countByCustomer(@Param("customer") User customer);
    @Query("SELECT SUM(o.totalPrice) FROM LaundryOrder o WHERE o.customer = :customer")
    Double sumRevenueByCustomer(@Param("customer") User customer);
    @Query("SELECT s.serviceName , COUNT(li) as count FROM LaundryOrder o JOIN o.items li" +
            " JOIN li.service s" +
            " WHERE o.customer = :customer GROUP BY s.serviceName ORDER BY count DESC")
    List<Object[]> findFavouriteServicesByCustomer(@Param("customer") User customer);

}
