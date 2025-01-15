package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.dto.ProductDto;
import com.nbe3.cafemanagement.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable long id) {
        model.addAttribute("product", adminProductService.getProduct(id));
        return "admin/product/edit";
    }

    @PostMapping("/new")
    public String createProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/product/new";
        }
        adminProductService.create(product);
        return "redirect:/admin/product/main";
    }

    @PutMapping("/edit/{id}")
    public String editProduct(@Valid @ModelAttribute("product") ProductDto product,
                              BindingResult bindingResult,
                              Model model,
                              @PathVariable long id) {
        if (bindingResult.hasErrors()) {
            return "admin/product/edit";
        }
        adminProductService.update(product, id);
        return "redirect:/admin/product/main";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        adminProductService.deleteProduct(id);
        return "redirect:/admin/product/main";
    }
}
