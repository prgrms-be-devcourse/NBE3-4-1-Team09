package com.nbe3.cafemanagement.order;

import com.nbe3.cafemanagement.controller.OrderController;
import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.service.OrderDetailService;
import com.nbe3.cafemanagement.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderDetailService orderDetailService;

    @InjectMocks
    private OrderController orderController;


    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void t1() throws Exception {
        when(orderService.save(anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString())).thenReturn(new CustomerOrder());

        mockMvc.perform(post("/order/payment")
                        .param("email", "test@example.com")
                        .param("address", "123 Test Street")
                        .param("postcode", "12345")
                        .param("totalAmount", "5000")
                        .param("products", "[{\"id\":\"1\",\"name\":\"iceAmericano\",\"quantity\":1,\"price\":5000},{\"id\":\"2\",\"name\":\"Americano\",\"quantity\":1,\"price\":7000}]")
                        .param("addressDetail", "Apt 101")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/master/checkProduct"));

        verify(orderService, times(1))
                .save("test@example.com", "123 Test Street", "12345", 5000, "Apt 101");
        verify(orderDetailService, times(1))
                .save(any(CustomerOrder.class), eq("[{\"id\":\"1\",\"name\":\"iceAmericano\",\"quantity\":1,\"price\":5000},{\"id\":\"2\",\"name\":\"Americano\",\"quantity\":1,\"price\":7000}]"));
    }

    @Test
    void t2() throws Exception {

        mockMvc.perform(post("/order/payment")
                        .param("address", "123 Test Street")
                        .param("postcode", "12345")
                        .param("totalAmount", "5000")
                        .param("products", "[{\"id\":\"1\",\"name\":\"iceAmericano\",\"quantity\":1,\"price\":5000},{\"id\":\"2\",\"name\":\"Americano\",\"quantity\":1,\"price\":7000}]")
                        .param("addressDetail", "Apt 101")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));

        verifyNoInteractions(orderService, orderDetailService);
    }
}