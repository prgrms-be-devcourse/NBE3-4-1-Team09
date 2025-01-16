package com.nbe3.cafemanagement.dto;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {
    //CustomerOrder 엔티티 속성
    private String email;
    private String address;
    private LocalDate orderDate;
    private CustomerOrder.OrderStatus status;
    private int totalPrice;
    private Long orderId;

    //Product 엔티티 속성
    private List<ProductExtendedInfo> products;

}
