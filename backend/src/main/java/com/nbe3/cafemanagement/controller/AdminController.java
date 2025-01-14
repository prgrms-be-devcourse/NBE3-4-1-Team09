package com.nbe3.cafemanagement.controller;


import com.nbe3.cafemanagement.dto.AdminDto;
import com.nbe3.cafemanagement.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {



    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        System.out.println("AdminController initialized");
    }

    // 관리자 로그인 화면
    @GetMapping("/login")
    public String loginPage() {
        System.out.println("Rendering template: admin/admin_login");
        return "admin/admin_login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(AdminDto adminDto, Model model) {
        boolean loginSuccess = adminService.login(adminDto);

        if (loginSuccess) {
            return "redirect:/admin/main";  // 성공 시 관리자 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "admin/admin_login";  // 실패 시 로그인 화면으로 다시 이동
        }
    }

    // 관리자 메인 페이지
    @GetMapping("/main")
    public String adminMainPage() {
        return "admin/admin_main";  // admin 폴더 내 admin_main.html 반환
    }

    // 주문 관리 페이지
    @GetMapping("/order")
    public String adminOrderPage() {
        return "admin/admin_order";  // admin 폴더 내 admin_order.html 반환
    }

    // 상품 관리 페이지
    @GetMapping("/product")
    public String adminProductPage() {
        return "admin/admin_product";  // admin 폴더 내 admin_product.html 반환
    }
}