package com.nbe3.cafemanagement.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderRequest {
    private String sortBy = "";
    private String searchParam = "";
    private String userEmail = "";
    private int page = 1;
    private int pageSize = 30;
}
