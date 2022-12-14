package com.accenture.ws.repository;

import com.accenture.ws.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderNameContaining(String orderName);
}
