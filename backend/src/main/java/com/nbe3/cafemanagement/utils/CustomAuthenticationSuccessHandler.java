package com.nbe3.cafemanagement.utils;

import com.nbe3.cafemanagement.service.AdminService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AdminService adminService;

    public CustomAuthenticationSuccessHandler(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        // 로그인 성공 시 실패 횟수 초기화
        adminService.resetFailedAttempts(username);

        response.sendRedirect("/admin/main");
    }
}