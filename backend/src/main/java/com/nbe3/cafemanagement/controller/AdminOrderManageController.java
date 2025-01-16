package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.dto.SetStateRequest;
import com.nbe3.cafemanagement.service.AdminOrderManageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminOrderManageController {

    private final AdminOrderManageService adminOrderManageService;

    @PutMapping("/admin/editStatus")
    public String editState(@Valid @RequestBody SetStateRequest setStateRequest) {
        adminOrderManageService.updateStatus(setStateRequest);
        return "admin/admin_order";
    }

}
