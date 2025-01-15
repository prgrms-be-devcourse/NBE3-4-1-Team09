package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProductRepository extends JpaRepository<Product, Integer> {

}