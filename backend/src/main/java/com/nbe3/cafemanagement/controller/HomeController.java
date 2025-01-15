package com.nbe3.cafemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")  // 루트 경로 요청 처리
    public String homePage() {

        // 초안 : templates 디렉토리의 main.html 파일을 반환
        // 2025-01-15 김누리 수정 : 상품 초기 화면 표시
        return "redirect:/master/checkProduct";
    }
}