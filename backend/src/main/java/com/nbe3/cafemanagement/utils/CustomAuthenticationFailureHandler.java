package com.nbe3.cafemanagement.utils;


import com.nbe3.cafemanagement.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final AdminService adminService;

    public CustomAuthenticationFailureHandler(AdminService adminService) {
        this.adminService = adminService;
    }



    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String username = request.getParameter("username");

        if (username != null) {
            if (adminService.isAccountBlocked(username)) {
                response.sendRedirect("/admin/login?error=blocked");
                return;
            }
            adminService.increaseFailedAttempts(username);
        }

        response.sendRedirect("/admin/login?error=true");
    }





}