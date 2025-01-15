package com.nbe3.cafemanagement.domain.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentForm {

    @NotEmpty(message = "이메일을 입력하세요")
    private String email;
    @NotEmpty(message = "주소를 입력하세요")
    private String address;
    @NotEmpty(message = "우편번호를 입력하세요")
    private String postcode;
}
