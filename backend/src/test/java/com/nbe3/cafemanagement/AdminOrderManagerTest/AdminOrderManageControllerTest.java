package com.nbe3.cafemanagement.AdminOrderManagerTest;

import com.nbe3.cafemanagement.dto.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 빈 등록
public class AdminOrderManageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("root")
    public void helloTest() throws Exception {
        mockMvc.perform(put("/admin/editStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\": \"1\", \"status\": \"배송완료\"}")
                .with(csrf())// 요청 본문 (JSON)
            )
           // "/hello" 요청 수행
            .andExpect(status().isOk()); // HTTP 상태 코드 200 확인
    }
}
