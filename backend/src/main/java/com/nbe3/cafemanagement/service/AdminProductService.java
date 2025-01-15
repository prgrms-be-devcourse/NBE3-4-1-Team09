package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.dto.ProductDto;
import com.nbe3.cafemanagement.repository.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;

    public void create(ProductDto productDto) {

        if (adminProductRepository.existsByName(productDto.getName())) {
            throw new RuntimeException("중복된 이름입니다.");
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        adminProductRepository.save(product);
    }
}
