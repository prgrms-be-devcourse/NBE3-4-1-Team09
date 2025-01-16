package com.nbe3.cafemanagement.dto;

import com.nbe3.cafemanagement.domain.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductExtendedInfo {
    private Product product;
    private int quantity;
    private int price;
}
