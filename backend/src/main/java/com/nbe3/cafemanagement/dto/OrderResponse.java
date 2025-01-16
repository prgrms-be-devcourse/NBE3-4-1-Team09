package com.nbe3.cafemanagement.dto;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {
    //Order 엔티티 속성
    private String email;
    private String address;
    private LocalDate orderDate;
    private Order.OrderStatus status;
    private int totalPrice;
    private Long orderId;

    //Product 엔티티 속성
    private List<ProductExtendedInfo> products;

}
