package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.dto.ProductDto;
import com.nbe3.cafemanagement.repository.AdminProductRepository;
import com.nbe3.cafemanagement.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;
    private final OrderDetailRepository orderDetailRepository;

    public void create(ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());


        adminProductRepository.save(product);
    }

    public void update(ProductDto productDto, long id) {

        Product product = new Product();
        product.setId(id);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        adminProductRepository.save(product);
    }

    public ProductDto getProduct(long id) {
        Product product = adminProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 상품 id 입니다, id=%d".formatted(id)));


        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    @Transactional
    public void deleteProduct(long id) {
        Product product = adminProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 상품 id 입니다, id=%d".formatted(id)));

        product.setDeleted(true);
        adminProductRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return adminProductRepository.findByDeletedFalse();
    }

    public void toggleActive(long id) {
        Product product = adminProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 상품 id 입니다, id=%d".formatted(id)));

        product.setInactive(!product.isInactive());
        adminProductRepository.save(product);
    }
}
