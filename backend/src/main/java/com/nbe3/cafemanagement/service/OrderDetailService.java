package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail save() {

        return null;
    }
}
