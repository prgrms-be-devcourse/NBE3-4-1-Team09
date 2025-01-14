package com.nbe3.cafemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/master")
public class MainController {

    @GetMapping("/checkProduct")
    public String checkProduct() {
        return "main";
    }
}
