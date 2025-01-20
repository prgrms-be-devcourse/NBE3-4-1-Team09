package com.nbe3.cafemanagement.AdminOrderManagerTest;

import com.nbe3.cafemanagement.dto.OrderRequest;
import com.nbe3.cafemanagement.dto.OrderResponse;
import com.nbe3.cafemanagement.service.AdminOrderManageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithUserDetails;
import static org.hamcrest.Matchers.hasSize; // 이 import 추가!


@SpringBootTest
@AutoConfigureMockMvc // MockMvc 빈 등록
public class AdminCustomerOrderManageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdminOrderManageService adminOrderManageService;

    @Test
    @WithUserDetails("root")
    public void updateTest1() throws Exception {
        mockMvc.perform(post("/admin/editStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\": \"1\", \"status\": \"SHIPPING\"}")
                .with(csrf())// 요청 본문 (JSON)
            ).andExpect(status().isOk()); // HTTP 상태 코드 200 확인
    }

    @Test
    @WithUserDetails("root")
    public void updateTest2() throws Exception {
        mockMvc.perform(post("/admin/editStatus")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"orderId\": \"1\", \"status\": \"PREPARING\"}")
            .with(csrf())// 요청 본문 (JSON)
        ).andExpect(status().isOk()); // HTTP 상태 코드 200 확인
    }

    @Test
    @WithUserDetails("root")
    public void updateTest3() throws Exception {
        mockMvc.perform(post("/admin/editStatus")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"orderId\": \"1\", \"status\": \"DELIVERED\"}")
            .with(csrf())// 요청 본문 (JSON)
        ).andExpect(status().isOk()); // HTTP 상태 코드 200 확인
    }

    @Test
    @WithUserDetails("root")
    public void updateTest4() throws Exception {
        mockMvc.perform(post("/admin/editStatus")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"orderId\": \"1\", \"status\": \"asdfasd\"}")
            .with(csrf())// 요청 본문 (JSON)
        ).andExpect(status().isBadRequest()); // HTTP 상태 코드 200 확인
    }

    @Test
    @WithUserDetails("root")
    public void updateTest5() throws Exception {
        mockMvc.perform(post("/admin/editStatus")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"orderId\": \"1\"}")
            .with(csrf())// 요청 본문 (JSON)
        ).andExpect(status().isBadRequest()); // HTTP 상태 코드 200 확인
    }

    @Test
    @WithUserDetails("root")
    public void order1() throws Exception {
        mockMvc.perform(get("/admin/order"))
            .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("root")
    public void order2() throws Exception {
        mockMvc.perform(get("/admin/order?userEmail=oungsi1000"))
            .andExpect(status().isOk())
            .andExpect(model().attributeErrorCount("orderRequest", 0))
        ;
    }

    @Test
    @WithUserDetails("root")
    public void order3() throws Exception {
        mockMvc.perform(get("/admin/order?dayFrom=2024-01-01"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("orderRequest"))
            .andExpect(model().attributeErrorCount("orderRequest", 1))
        ;
    }

    @Test
    @WithUserDetails("root")
    public void order4() throws Exception {
        mockMvc.perform(get("/admin/order?dayFrom=2024-01-01&dayUntil=2024-01-20"))
            .andExpect(status().isOk())
            .andExpect(model().attributeErrorCount("orderRequest", 0))
        ;
    }

    @Test
    @WithUserDetails("root")
    public void order5() throws Exception {
        mockMvc.perform(get("/admin/order?dayFrom=2024-01-01&dayUntil=2025-01-20"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("orderRequest"))
            .andExpect(model().attributeErrorCount("orderRequest", 1))
        ;
    }

    @Test
    @WithUserDetails("root")
    public void order6() throws Exception {
        mockMvc.perform(get("/admin/order?dayFrom=20240-01-01&dayUntil=2025-01-20"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("orderRequest"))
            .andExpect(model().attributeErrorCount("orderRequest", 1))
        ;
    }
}
