package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderDetailDto;
import com.nbe3.cafemanagement.service.OrderDetailService;
import com.nbe3.cafemanagement.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderDetail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;
    private final OrderService orderService;

    @GetMapping()
    public String orderDetail(OrderDetailDto orderDetailDto) {

        return "order/orderDetail";
    }

    @PostMapping()
    @Transactional
    public String orderDetail(@Valid OrderDetailDto orderDetailDto, BindingResult bindingResult, Model model) {
        System.out.println(orderDetailDto.getEmail());

        if (bindingResult.hasErrors()) {
            return "order/orderDetail";
        }

        List<Order> orders = orderService.findByEmailOrderByOrderDate(orderDetailDto.getEmail());

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            orderDetails.addAll(orderDetailService.findByOrderId(order.getId()));
        }

        System.out.println(orders);
        System.out.println(orderDetails);

        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetails);

        return "order/orderDetail";
    }
}
