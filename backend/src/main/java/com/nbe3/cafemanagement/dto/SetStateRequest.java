package com.nbe3.cafemanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nbe3.cafemanagement.domain.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class SetStateRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private Order.OrderStatus status;
}
