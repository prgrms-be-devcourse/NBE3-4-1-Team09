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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String orderDetail(
            @ModelAttribute OrderDetailDto orderDetailDto,
            Model model
    ) {
        if (orderDetailDto.getEmail() == null || orderDetailDto.getEmail().isEmpty()) {
            return "order/orderDetail";
        }

        List<CustomerOrder> orders = orderService.getOrderList(
                orderDetailDto.getEmail(),
                orderDetailDto.getDayFrom(),
                orderDetailDto.getDayUntil()
        );

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CustomerOrder order : orders) {
            orderDetails.addAll(orderDetailService.findByCustomerOrderId(order.getId()));
        }

        Page<OrderDetail> orderDetailsPage = orderDetailService.pagination(orderDetails, orderDetailDto.getPage());

        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetailsPage.getContent());
        model.addAttribute("page", orderDetailsPage);
        model.addAttribute("email", orderDetailDto.getEmail());

        return "order/orderDetail";
    }

    @PostMapping()
    public String orderDetail(@Valid OrderDetailDto orderDetailDto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "order/orderDetail";
        }

        return "redirect:/orderDetail?email=%s&dayFrom=%s&dayUntil=%s"
                .formatted(
                        orderDetailDto.getEmail(),
                        orderDetailDto.getDayFrom(),
                        orderDetailDto.getDayUntil()
                );
    }
}
