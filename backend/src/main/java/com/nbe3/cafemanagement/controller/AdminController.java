package com.nbe3.cafemanagement.controller;



import com.nbe3.cafemanagement.dto.AdminDto;
import com.nbe3.cafemanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    // 관리자 로그인 화면
    @GetMapping("/login")
    public String loginPage(Model model, String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "admin/admin_login";
    }

    // 회원가입 페이지 요청 처리
    @GetMapping("/register")
    public String registerPage() {
        return "admin/admin_register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(AdminDto adminDto, Model model) {
        boolean registerSuccess = adminService.register(adminDto);

        if (registerSuccess) {
            model.addAttribute("registerMessage", "Registration successful! You can now log in.");
            return "redirect:/admin/login";  // 성공 시 로그인 페이지로 리다이렉트
        } else {
            model.addAttribute("registerMessage", "Registration failed. Username already exists.");
            return "admin/admin_register";  // 실패 시 회원가입 페이지로 다시 이동
        }
    }



    // 관리자 메인 페이지
    @GetMapping("/main")
    public String adminMainPage(Model model, Principal principal) {
        String username = principal.getName();  // 로그인한 사용자의 아이디(Username)를 가져옴
        model.addAttribute("username", username);
        return "admin/admin_main"; // admin 폴더 내 admin_main.html 반환
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