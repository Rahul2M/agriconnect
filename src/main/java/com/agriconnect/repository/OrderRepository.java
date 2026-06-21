package com.agriconnect.repository;

import com.agriconnect.entity.Order;
import com.agriconnect.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByBuyerId(Long buyerId);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

}