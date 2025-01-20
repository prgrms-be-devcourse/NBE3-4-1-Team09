package com.nbe3.cafemanagement.AdminProductTest;

import com.nbe3.cafemanagement.controller.AdminProductController;
import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.dto.ProductDto;
import com.nbe3.cafemanagement.repository.AdminProductRepository;
import com.nbe3.cafemanagement.service.AdminProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
public class AdminProductControllerTest {

    @Mock
    private AdminProductService adminProductService;

    @InjectMocks
    private AdminProductController adminProductController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminProductController).build();
    }

    @Test
    public void testProductMain() throws Exception {
        when(adminProductService.getAllProducts()).thenReturn(List.of(new Product())); // 더미 데이터 반환

        mockMvc.perform(get("/admin/product"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product/main"))
                .andExpect(model().attributeExists("products"));

        verify(adminProductService, times(1)).getAllProducts();
    }

    @Test
    public void testCreatePage() throws Exception {
        mockMvc.perform(get("/admin/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product/new"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testEditPage() throws Exception {
        long productId = 1L;
        when(adminProductService.getProduct(productId)).thenReturn(new ProductDto()); // 더미 데이터 반환

        mockMvc.perform(get("/admin/product/edit/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product/edit"))
                .andExpect(model().attributeExists("product"));

        verify(adminProductService, times(1)).getProduct(productId);
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        doNothing().when(adminProductService).create(any(ProductDto.class));

        mockMvc.perform(post("/admin/product/new")
                        .param("name", "Test Product")
                        .param("price", "100")
                        .param("description", "A sample product description.")
                        .param("imageUrl", "https://example.com/image.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product"));

        verify(adminProductService, times(1)).create(any(ProductDto.class));
    }

    @Test
    public void testEditProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        long productId = 1L;

        mockMvc.perform(post("/admin/product/edit/{id}", productId)
                        .param("name", "Test Product")
                        .param("price", "100")
                        .param("description", "A sample product description.")
                        .param("imageUrl", "https://example.com/image.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product"));

        verify(adminProductService, times(1)).update(any(ProductDto.class), eq(productId));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        long productId = 1L;
        doNothing().when(adminProductService).deleteProduct(productId);

        mockMvc.perform(post("/admin/product/delete/{id}", productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product"));

        verify(adminProductService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testToggleActive() throws Exception {
        long productId = 1L;
        doNothing().when(adminProductService).toggleActive(productId);

        mockMvc.perform(post("/admin/product/inactive/{id}", productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product"));

        verify(adminProductService, times(1)).toggleActive(productId);
    }
}