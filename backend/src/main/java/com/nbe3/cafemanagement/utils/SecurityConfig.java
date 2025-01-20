package com.nbe3.cafemanagement.utils;

import com.nbe3.cafemanagement.service.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AdminService adminService) throws Exception {
        // 핸들러 생성
        AuthenticationFailureHandler authenticationFailureHandler = new CustomAuthenticationFailureHandler(adminService);
        AuthenticationSuccessHandler authenticationSuccessHandler = new CustomAuthenticationSuccessHandler(adminService);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/admin/login",
                                "/admin/register",
                                "/admin/order",
                                "/admin/editStatus",
                                "/css/**",
                                "/js/**",
                                "/master/checkProduct",
                                "/order/payment",
                                "/orderDetail",
                                "/orderDetail/**").permitAll()  // 인증 없이 접근 허용
                        .requestMatchers("/admin/**").authenticated()  // /admin/** 경로는 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/main", true)
                        .successHandler(authenticationSuccessHandler) // 성공 핸들러 설정
                        .failureHandler(authenticationFailureHandler) // 실패 핸들러 설정
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .permitAll()
                )
                 .csrf(csrf -> csrf.disable())
                ;
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();

            // 로그인 성공 시 실패 횟수 초기화
            response.sendRedirect("/admin/main");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화를 위한 BCryptPasswordEncoder 사용
    }
}