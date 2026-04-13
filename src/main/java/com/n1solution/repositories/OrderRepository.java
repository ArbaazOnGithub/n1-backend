package com.n1solution.repositories;

import com.n1solution.dto.OrderDetailsDTO;
import com.n1solution.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT new com.n1solution.dto.OrderDetailsDTO(" +
           "o.id, o.serviceType, o.date, o.status, u.name, u.mobile) " +
           "FROM Order o JOIN o.user u")
    List<OrderDetailsDTO> findAllOrderDetailsWithUserInfo();
    
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    
    @Query("SELECT o FROM Order o JOIN FETCH o.details WHERE o.id = :orderId")
    Optional<Order> findOrderWithDetailsById(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.date DESC")
    List<Order> findByUserId(@Param("userId") Long userId);
}