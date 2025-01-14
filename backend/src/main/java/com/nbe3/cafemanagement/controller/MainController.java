package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/master")
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    // 상품 목록 페이지
    @GetMapping("/checkProduct")
    public String checkProduct(Model model) {

        List<Product> products = mainService.getAllProducts();

        System.out.println("상품 목록: " + products);

        model.addAttribute("products", products);

        return "main";
    }
}
