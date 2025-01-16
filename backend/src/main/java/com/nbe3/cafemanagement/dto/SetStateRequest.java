package com.nbe3.cafemanagement.dto;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SetStateRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private CustomerOrder.OrderStatus status;
}
