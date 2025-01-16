package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.dto.ProductExtendedInfo;
import com.nbe3.cafemanagement.dto.SetStateRequest;
import com.nbe3.cafemanagement.repository.AdminOrderManageRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminOrderManageService {
    private final AdminOrderManageRepository adminOrderManageRepository;

    public Page<OrderResponse> getList(OrderRequest orderRequest) {
        //Todo 정렬 기준 합의 후 설정 기본값은 주문 순으로
        Pageable pageable = PageRequest.of(orderRequest.getPage()-1, orderRequest.getPageSize(), Sort.by(Sort.Direction.DESC, "order.orderDate"));
        Map<Long, List<ProductExtendedInfo>> productMap = new HashMap<>();

        List<OrderResponse> orderResponses = adminOrderManageRepository.findAll(orderRequest.getUserEmail(), orderRequest.getSearchParam()).stream().map(
            orderDetail -> {
                List<ProductExtendedInfo> list = productMap.computeIfAbsent(orderDetail.getOrder().getId(), it->new ArrayList<>());
                ProductExtendedInfo productDto = new ProductExtendedInfo();
                productDto.setProduct(orderDetail.getProduct());
                productDto.setPrice(orderDetail.getPrice());
                productDto.setQuantity(orderDetail.getQuantity());
                list.add(productDto);
                if (list.size() != 1) return null;
                return toOrderResponse(orderDetail,productMap);
            }
        ).toList();
        List<OrderResponse> discarded = orderResponses.stream().filter(Objects::nonNull).toList();
        return new PageImpl<>(discarded, pageable, discarded.size());
    }

    public void updateStatus(SetStateRequest setStateRequest) {
        List<OrderDetail> orderDetails = adminOrderManageRepository.findByOrder_Id(setStateRequest.getOrderId());
        orderDetails.forEach(
            orderDetail-> {
                orderDetail.getOrder().setStatus(setStateRequest.getStatus());
                adminOrderManageRepository.save(orderDetail);
            }
        );
    }

    private OrderResponse toOrderResponse(OrderDetail orderDetail, Map<Long, List<ProductExtendedInfo>> productMap) {
        Order order = orderDetail.getOrder();
        return OrderResponse.builder()
            .email(order.getEmail())
            .address(order.getAddress())
            .orderDate(order.getOrderDate())
            .status(order.getStatus())
            .totalPrice(order.getTotalPrice())
            .orderId(order.getId())
            .quantity(orderDetail.getQuantity())
            .price(orderDetail.getPrice())
            .products(productMap.get(order.getId()))
            .build();
    }
}
