package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.service.AdminOrderManageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminOrderManageController {
    private final AdminOrderManageService adminOrderManageService;

    @GetMapping("/admin/order")
    //Todo @PreAuthorize 또는 securityConfig 설정
    public String orderList(Model model,
                            @Valid @ModelAttribute OrderRequest orderRequest) {
        Page<OrderResponse> list = adminOrderManageService.getList(orderRequest);
        model.addAttribute("orderList", list);
        return "admin_order";
    }

    @PutMapping("/admin/edit")
    //Todo @PreAuthorize 또는 securityConfig 설정
    public String editState() {
        return "admin_order";
    }
}
