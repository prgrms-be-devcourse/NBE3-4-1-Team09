package com.nbe3.cafemanagement.dto;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Getter
@Setter
@Builder
public class OrderResponse {
    //Order 엔티티 속성
    private String email;
    private String address;
    private Date orderDate;
    private Order.OrderStatus status;
    private int totalPrice;

    //OrderDetail 엔티티 속성
    private int quantity;
    private int price;

    //Product 엔티티 속성
    private String name;
    private String imageUrl;

}
