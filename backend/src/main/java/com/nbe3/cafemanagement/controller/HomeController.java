package com.nbe3.cafemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")  // 루트 경로 요청 처리
    public String homePage() {
        return "main";  // templates 디렉토리의 main.html 파일을 반환
    }
}