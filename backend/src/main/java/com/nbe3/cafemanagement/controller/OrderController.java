package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.dto.OrderDto;
import com.nbe3.cafemanagement.service.OrderDetailService;
import com.nbe3.cafemanagement.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
            @RequestParam(name = "totalAmount", defaultValue = "0") int totalAmount,
            @RequestParam(name = "products", defaultValue = "") String products,
            @RequestParam(name = "addressDetail", defaultValue = "") String addressDetail
                          ) {

        if (bindingResult.hasErrors()) {
            return "main";
        }

        CustomerOrder order = orderService.save(
                orderDto.getEmail(),
                orderDto.getAddress(),
                orderDto.getPostcode(),
                totalAmount,
                addressDetail);
        orderDetailService.save(order, products);

        return "redirect:/master/checkProduct";
    }
}
