package com.nbe3.cafemanagement.dto;

import com.nbe3.cafemanagement.domain.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
public class OrderDto {

    @NotEmpty(message = "이메일을 입력하세요")
    private String email;

    @NotEmpty(message = "주소를 입력하세요")
    private String address;

    @NotEmpty(message = "우편번호를 입력하세요")
    private String postcode;

    private LocalDate orderDate;

    private Order.OrderStatus status;

    private int totalPrice;

    public OrderDto() {

    }
    public OrderDto(Order order) {
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
    }
}
