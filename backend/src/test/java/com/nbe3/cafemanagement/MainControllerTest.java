package com.nbe3.cafemanagement;

import com.nbe3.cafemanagement.controller.MainController;
import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.service.MainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


@SpringBootTest
public class MainControllerTest {

    @Mock
    private MainService mainService;

    @InjectMocks
    private MainController mainController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    @DisplayName("초기화면 표시 테스트")
    void testCheckProduct() throws Exception {
        when(mainService.getNotDeletedProducts()).thenReturn(List.of(new Product())); //더미 데이터

        mockMvc.perform(MockMvcRequestBuilders.get("/master/checkProduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("products"));

        verify(mainService).getNotDeletedProducts();
    }
}
