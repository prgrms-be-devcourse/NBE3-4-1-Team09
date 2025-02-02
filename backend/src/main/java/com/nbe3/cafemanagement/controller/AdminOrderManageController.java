package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.dto.SetStateRequest;
import com.nbe3.cafemanagement.service.AdminOrderManageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminOrderManageController {

    private final AdminOrderManageService adminOrderManageService;

    @PostMapping("/admin/editStatus")
    @ResponseBody
    public ResponseEntity<String> editState(@Valid @RequestBody SetStateRequest setStateRequest) {
        adminOrderManageService.updateStatus(setStateRequest);
        return ResponseEntity.ok().body("성공");
    }
}
