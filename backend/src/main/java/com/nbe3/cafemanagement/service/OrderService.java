package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order save(String email, String address, String postcode, int totalPrice) {
        Order order = Order.builder()
                .email(email)
                .address(address + "|" + postcode)
                .totalPrice(totalPrice)
                .orderDate(LocalDate.now())
                .status(Order.OrderStatus.PREPARING)
                .build();
        
        return orderRepository.save(order);
    }
}
