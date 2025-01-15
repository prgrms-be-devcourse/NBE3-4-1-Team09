package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderDto;
import com.nbe3.cafemanagement.service.OrderDetailService;
import com.nbe3.cafemanagement.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @PostMapping("/payment")
    public String payment(
            @Valid OrderDto orderDto,
            BindingResult bindingResult,
            @RequestParam(name = "totalPrice", defaultValue = "0") int totalPrice,
            @RequestParam(name = "summary", defaultValue = "") String summary
                          ) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        Order order = orderService.save(orderDto.getEmail(), orderDto.getAddress(), orderDto.getPostcode(), totalPrice);
        OrderDetail orderDetail = orderDetailService.save();

        return "redirect:/master/checkProduct";
    }
}
