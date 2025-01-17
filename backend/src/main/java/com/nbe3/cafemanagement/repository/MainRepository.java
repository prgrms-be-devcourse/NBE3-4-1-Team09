package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainRepository extends JpaRepository<Product, Long> {
    List<Product> findByDelFlagFalse();
}
