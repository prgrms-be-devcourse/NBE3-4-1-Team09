package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.repository.AdminOrderManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminOrderManageService {
    private final AdminOrderManageRepository adminOrderManageRepository;

    public Page<OrderResponse> getList(OrderRequest orderRequest) {
        //Todo 정렬 기준 합의 후 설정 기본값은 주문 순으로
        Pageable pageable = PageRequest.of(orderRequest.getPage(), orderRequest.getPageSize(), Sort.by(Sort.Direction.DESC, "orderDate"));
        return adminOrderManageRepository.findAll(orderRequest.getUserEmail(), orderRequest.getSearchParam(), pageable).map(
            this::toOrderResponse
        );
    }

    public OrderResponse toOrderResponse(OrderDetail orderDetail) {
        return OrderResponse.builder()
            .build();
    }
}
