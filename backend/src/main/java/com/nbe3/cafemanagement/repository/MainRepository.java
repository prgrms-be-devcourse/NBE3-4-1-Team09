package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRepository extends JpaRepository<Product, Long> {
}
