package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.dto.ProductDto;
import com.nbe3.cafemanagement.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping("/main")
    public String productMain() {
        return "admin/product/main";
    }

    @GetMapping("/new")
    public String createPage(Model model) {
        model.addAttribute("product", new ProductDto());
        return "admin/product/new";
    }

    @PostMapping("/new")
    public String createProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/product/new";
        }
        adminProductService.create(product);
        return "redirect:/admin/product/main";
    }

//    @GetMapping("/edit")
//    public String editPage() {
//        return "admin/product/admin_product_edit";
//    }
}
