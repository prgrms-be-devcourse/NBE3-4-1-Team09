package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmailOrderByOrderDate(String email);
}
