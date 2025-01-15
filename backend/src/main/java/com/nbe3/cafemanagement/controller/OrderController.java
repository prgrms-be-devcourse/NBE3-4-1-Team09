package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.domain.form.PaymentForm;
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
    public String payment(@Valid PaymentForm paymentForm,
                          @RequestParam(name = "totalPrice", defaultValue = "0") int totalPrice,
                          @RequestParam(name = "summary", defaultValue = "") String summary,
                          BindingResult bindingResult
                          ) {
        if (bindingResult.hasErrors()) {
            System.out.println("여기1");
            return "main";
        }

        System.out.println("여기2");
        Order order = orderService.save(paymentForm.getEmail(), paymentForm.getAddress(), paymentForm.getPostcode(), totalPrice);
        OrderDetail orderDetail = orderDetailService.save();

        return "redirect:/master/checkProduct";
    }
}
