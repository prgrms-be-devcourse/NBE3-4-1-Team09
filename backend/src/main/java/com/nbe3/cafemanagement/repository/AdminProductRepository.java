package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
}