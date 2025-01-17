package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.repository.MainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    private final MainRepository mainRepository;


    public MainService(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    // 모든 상품 목록 조회
    //  2025-01-17 : 물리 삭제 >> 논리 삭제 변경
/*    public List<Product> getAllProducts() {
        return mainRepository.findAll();
    }*/

    // 모든 상품 목록 조회
    public List<Product> getNotDeletedProducts() {
        return mainRepository.findByDeletedFalseAndInactiveFalse();
    }
}
