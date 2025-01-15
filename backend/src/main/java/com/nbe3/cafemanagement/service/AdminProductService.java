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

    public void update(ProductDto productDto, int id) {
        if (!adminProductRepository.existsById(id)) {
            throw new RuntimeException("존재하지 않는 상품입니다.");
        }

        Product product = new Product();
        product.setId(id);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        adminProductRepository.save(product);
    }

    public ProductDto getProduct(int id) {
        Product product = adminProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품 id 입니다, id=%d".formatted(id)));

        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
