package com.nbe3.cafemanagement.controller;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderDetailDto;
import com.nbe3.cafemanagement.service.OrderDetailService;
import com.nbe3.cafemanagement.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderDetail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;
    private final OrderService orderService;

    @GetMapping()
    public String orderDetail(
            OrderDetailDto orderDetailDto,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {
        if (email == null || email.isEmpty()) {
            return "order/orderDetail";
        }

        List<CustomerOrder> orders = orderService.findByEmailOrderByCreatedAtDesc(email);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CustomerOrder order : orders) {
            orderDetails.addAll(orderDetailService.findByCustomerOrderId(order.getId()));
        }

        Page<OrderDetail> orderDetailsPage = orderDetailService.pagination(orderDetails, page);

        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetailsPage.getContent());
        model.addAttribute("page", orderDetailsPage);
        model.addAttribute("email", email);

        return "order/orderDetail";
    }

    @PostMapping()
    @Transactional
    public String orderDetail(@Valid OrderDetailDto orderDetailDto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "order/orderDetail";
        }

        return "redirect:/orderDetail?email=%s".formatted(orderDetailDto.getEmail());
    }
}
