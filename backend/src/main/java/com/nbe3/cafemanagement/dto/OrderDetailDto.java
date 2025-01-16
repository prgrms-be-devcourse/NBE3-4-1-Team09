package com.nbe3.cafemanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDto {
    @NotEmpty(message = "이메일을 입력하세요")
    private String email;
}
