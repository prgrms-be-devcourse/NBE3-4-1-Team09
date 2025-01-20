package com.nbe3.cafemanagement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class OrderDetailDto {
    @NotEmpty(message = "이메일을 입력하세요")
    private String email;
    private LocalDate dayFrom = LocalDate.now().minusWeeks(1);
    private LocalDate dayUntil = LocalDate.now();
    private int page;

    @AssertTrue(message = "날짜는 최대 1개월간 조회할 수 있습니다")
    public boolean getIsDateValid() {
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minus(Period.ofMonths(1));
        return(dayFrom.isAfter(minDate) || dayFrom.isEqual(minDate))
                &&( dayFrom.isBefore(today) || dayFrom.isEqual(today))
                && (dayUntil.isAfter(minDate) || dayUntil.isEqual(minDate))
                &&( dayUntil.isBefore(today) || dayUntil.isEqual(today));
    }

    @AssertTrue(message = "날짜를 다시 선택해주세요.")
    public boolean getIsDateCompareValid() {
        return !dayUntil.isBefore(dayFrom);
    }
}
