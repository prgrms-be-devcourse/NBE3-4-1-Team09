package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.dto.ProductExtendedInfo;
import com.nbe3.cafemanagement.dto.SetStateRequest;
import com.nbe3.cafemanagement.repository.AdminOrderManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminOrderManageService {
    private final AdminOrderManageRepository adminOrderManageRepository;

    public Page<OrderResponse> getList(OrderRequest orderRequest) {
        //Todo 정렬 기준 합의 후 설정 기본값은 주문 순으로
        Pageable pageable = PageRequest.of(orderRequest.getPage()-1, orderRequest.getPageSize(), Sort.by(Sort.Direction.DESC, "customerOrder.orderDate"));
        Map<Long, List<ProductExtendedInfo>> productMap = new HashMap<>();

        List<OrderResponse> orderResponses = adminOrderManageRepository.findAll(
            orderRequest.getUserEmail(),
            orderRequest.getSearchParam(),
            orderRequest.getDayFrom(),
            orderRequest.getDayUntil()
        ).stream().map(
            orderDetail -> {
                List<ProductExtendedInfo> list = productMap.computeIfAbsent(orderDetail.getCustomerOrder().getId(), it->new ArrayList<>());
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

        int pageStart = Math.min((orderRequest.getPage()-1)*orderRequest.getPageSize(), discarded.size());
        int pageEnd = Math.min(pageStart + orderRequest.getPageSize(), discarded.size());
        return new PageImpl<>(discarded.subList(pageStart, pageEnd), pageable, discarded.size());
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



    private OrderResponse toOrderResponse(OrderDetail orderDetail, Map<Long, List<ProductExtendedInfo>> productMap) {
        CustomerOrder customerOrder = orderDetail.getCustomerOrder();
        return OrderResponse.builder()
            .email(customerOrder.getEmail())
            .address(customerOrder.getAddress())
            .orderDate(customerOrder.getOrderDate())
            .status(customerOrder.getStatus())
            .totalPrice(customerOrder.getTotalPrice())
            .orderId(customerOrder.getId())
            .products(productMap.get(customerOrder.getId()))
            .build();
    }
}
