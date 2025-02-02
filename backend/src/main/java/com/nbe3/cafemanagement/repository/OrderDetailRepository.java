package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByCustomerOrderId(Long id);
}
