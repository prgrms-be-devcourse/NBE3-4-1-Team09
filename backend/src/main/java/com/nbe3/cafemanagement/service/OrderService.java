package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public CustomerOrder save(String email, String address, String postcode, int totalAmount) {
        CustomerOrder customerOrder = CustomerOrder.builder()
                .email(email)
                .address(address + "|" + postcode)
                .totalPrice(totalAmount)
                .orderDate(LocalDate.now())
                .status(CustomerOrder.OrderStatus.PREPARING)
                .build();
        
        return orderRepository.save(customerOrder);
    }

    public List<CustomerOrder> findAll() {
        return orderRepository.findAll();
    }

    public Optional<CustomerOrder> findById(long id) {
        return orderRepository.findById(id);
    }
}
