package com.nbe3.cafemanagement.order;

import com.nbe3.cafemanagement.controller.OrderDetailController;
import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.service.OrderDetailService;
import com.nbe3.cafemanagement.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class OrderDetailControllerTest {

    @Mock
    private OrderDetailService orderDetailService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderDetailController orderDetailController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderDetailController).build();
    }

    @Test
    public void t1() throws Exception {
        mockMvc.perform(get("/orderDetail"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderDetail"));
    }

    @Test
    public void t2() throws Exception {
        // Mock 데이터 설정
        List<CustomerOrder> mockOrders = new ArrayList<>();
        mockOrders.add(new CustomerOrder());
        List<OrderDetail> mockOrderDetails = new ArrayList<>();
        mockOrderDetails.add(new OrderDetail());

        Page<OrderDetail> mockPage = new PageImpl<>(mockOrderDetails);

        when(orderService.getOrderList(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(mockOrders);
        when(orderDetailService.findByCustomerOrderId(anyLong())).thenReturn(mockOrderDetails);
        when(orderDetailService.pagination(anyList(), anyInt())).thenReturn(mockPage);

        mockMvc.perform(get("/orderDetail")
                        .param("email", "test@example.com")
                        .param("dayFrom", "2023-01-01")
                        .param("dayUntil", "2023-12-31")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderDetail"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("orderDetails"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("email", "test@example.com"));
    }

    @Test
    public void t3() throws Exception {
        mockMvc.perform(post("/orderDetail")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@example.com")
                        .param("dayFrom", "2023-01-01")
                        .param("dayUntil", "2023-12-31"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderDetail"));
    }

    @Test
    public void t4() throws Exception {
        mockMvc.perform(post("/orderDetail")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "")
                        .param("dayFrom", "2023-01-01")
                        .param("dayUntil", "2023-12-31"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderDetail"));
    }
}
