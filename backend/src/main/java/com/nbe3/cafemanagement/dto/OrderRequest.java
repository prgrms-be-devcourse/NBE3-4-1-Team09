package com.nbe3.cafemanagement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderRequest {
    private String sortBy = "";
    private String searchParam = "";
    private String userEmail = "";
    private LocalDate dayFrom = LocalDate.now().minusWeeks(1);
    private LocalDate dayUntil = LocalDate.now();
    private int page = 1;
    private int pageSize = 10;

    @AssertTrue(message = "날짜는 최대 1개월 단위로 조회할 수 있습니다")
    public boolean isDateValid() {
        LocalDate today = LocalDate.now();
        LocalDate maxDate = dayFrom.plus(Period.ofMonths(1));
        return ( dayFrom.isBefore(today) || dayFrom.isEqual(today))
            && ( dayUntil.isBefore(maxDate) || dayUntil.isEqual(maxDate))
            && ( dayUntil.isBefore(today) || dayUntil.isEqual(today)
        );
    }
}
