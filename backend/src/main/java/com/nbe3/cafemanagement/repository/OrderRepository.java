package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByEmailOrderByCreatedAtDesc(String email);

    List<CustomerOrder> findByEmailAndOrderDateBetweenOrderByCreatedAtDesc(String email, LocalDate dayFrom, LocalDate dayUntil);
}
