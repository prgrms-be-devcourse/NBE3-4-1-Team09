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

    public CustomerOrder save(String email, String address, String postcode, int totalAmount, String addressDetail) {
        CustomerOrder order = CustomerOrder.builder()
                .email(email)
                .address(address + " "+ addressDetail+"|" + postcode)
                .totalPrice(totalAmount)
                .orderDate(LocalDate.now())
                .status(CustomerOrder.OrderStatus.PREPARING)
                .build();
        
        return orderRepository.save(order);
    }

    public List<CustomerOrder> findAll() {
        return orderRepository.findAll();
    }

    public Optional<CustomerOrder> findById(long id) {
        return orderRepository.findById(id);
    }

    public List<CustomerOrder> findByEmailOrderByCreatedAtDesc(String email) {
        return orderRepository.findByEmailOrderByCreatedAtDesc(email);
    }

    public List<CustomerOrder> getOrderList(String email, LocalDate dayFrom, LocalDate dayUntil) {
        return orderRepository.findByEmailAndOrderDateBetweenOrderByCreatedAtDesc(email, dayFrom, dayUntil);
    }
}
