package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.dto.ProductExtendedInfo;
import com.nbe3.cafemanagement.dto.SetStateRequest;
import com.nbe3.cafemanagement.repository.AdminOrderManageRepository;
import com.nbe3.cafemanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminOrderManageService {
    private final AdminOrderManageRepository adminOrderManageRepository;
    private final OrderRepository orderRepository;

    public Page<OrderResponse> getList(OrderRequest orderRequest) {
        //Todo 정렬 기준 합의 후 설정 기본값은 주문 순으로
        Pageable pageable = PageRequest.of(orderRequest.getPage()-1, orderRequest.getPageSize(), Sort.by(Sort.Direction.DESC, "orderDate"));
        return orderRepository.findAll(
            orderRequest.getUserEmail(),
            orderRequest.getDayFrom(),
            orderRequest.getDayUntil(),
            pageable).map(
            this::toOrderResponse
        );
    }

    public List<String> getAllUsers() {
        return adminOrderManageRepository.findAll().stream().map(
            orderDetail -> orderDetail.getCustomerOrder().getEmail()
        ).distinct().toList();
    }

    public void updateStatus(SetStateRequest setStateRequest) {
        List<OrderDetail> orderDetails = adminOrderManageRepository.findByCustomerOrder_Id(setStateRequest.getOrderId());
        orderDetails.forEach(
            orderDetail-> {
                orderDetail.getCustomerOrder().setStatus(setStateRequest.getStatus());
                adminOrderManageRepository.save(orderDetail);
            }
        );
    }



    private OrderResponse toOrderResponse(CustomerOrder customerOrder) {
        return OrderResponse.builder()
            .email(customerOrder.getEmail())
            .address(customerOrder.getAddress())
            .orderDate(customerOrder.getOrderDate())
            .status(customerOrder.getStatus())
            .totalPrice(customerOrder.getTotalPrice())
            .orderId(customerOrder.getId())
            .products(toProductExtendedInfo(customerOrder.getOrderDetails()))
            .build();
    }

    private List<ProductExtendedInfo> toProductExtendedInfo(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(
            orderDetail -> {
                ProductExtendedInfo productExtendedInfo = new ProductExtendedInfo();
                productExtendedInfo.setPrice(orderDetail.getPrice());
                productExtendedInfo.setQuantity(orderDetail.getQuantity());
                productExtendedInfo.setProduct(orderDetail.getProduct());
                return productExtendedInfo;
            }
        ).toList();
    }
}
