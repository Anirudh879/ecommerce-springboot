package com.codeWithProjects.ecom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.convention.TestBean;
import org.springframework.test.web.servlet.MockMvc;

import com.codeWithProject.ecom.controller.ProductController;
import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.serviceImpl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@Autowired
    private MockMvc mockMvc;

	@TestBean
	private ProductServiceImpl productServiceImpl = Mockito.mock(ProductServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;
    
    
    @Test
    void testAddProduct() throws Exception {
    	  ProductDto dto = new ProductDto();
          dto.setId(1L);
          dto.setName("Laptop");
          dto.setDescription("All kinds of laptop");
          dto.setCategoryId(10L);
          
          MockMultipartFile productJson =
                  new MockMultipartFile(
                          "product",
                          "",
                          "application/json",
                          objectMapper.writeValueAsBytes(dto)
                  );
          
          MockMultipartFile image =
                  new MockMultipartFile(
                          "image",
                          "photo.png",
                          "image/png",
                          "fake-image".getBytes()
                  );
          
          when(productServiceImpl.addProduct(any(), any())).thenReturn(dto);
          
          mockMvc.perform(
                  multipart("/api/admin/products")
                          .file(productJson)
                          .file(image)
                          .contentType(MediaType.MULTIPART_FORM_DATA)
          )
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.message").value("Product added Successfully"))
          .andExpect(jsonPath("$.data.name").value("Laptop"));

    }
    
    @Test
    void testGetAllProducts() throws Exception {
    	 ProductDto dto = new ProductDto();
         dto.setId(1L);
         dto.setName("Laptop");

         when(productServiceImpl.getAllProduct()).thenReturn(List.of(dto));

         mockMvc.perform(get("/api/admin/getProducts"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.data[0].name").value("Laptop"));
    }
    
    @Test
    void testGetProductsByName() throws Exception {
    	 ProductDto dto = new ProductDto();
         dto.setId(2L);
         dto.setName("Keyboard");

         when(productServiceImpl.getAllProductByName("Keyboard"))
                 .thenReturn(List.of(dto));

         mockMvc.perform(get("/api/admin/product/Keyboard"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.data[0].name").value("Keyboard"));
    }
    
    
    
    @Test
    void testDeleteProduct() throws Exception {
    	  doNothing().when(productServiceImpl).deleteProduct(3L);

          mockMvc.perform(delete("/api/admin/product/3"))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$.message").value("Product deleted"));
    }
    
    @Test
    void testUpdateProduct() throws Exception {
    	ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setName("Updated Laptop");

        MockMultipartFile productJson =
                new MockMultipartFile(
                        "product",
                        "",
                        "application/json",
                        objectMapper.writeValueAsBytes(dto)
                );

        MockMultipartFile image =
                new MockMultipartFile(
                        "image",
                        "newimage.png",
                        "image/png",
                        "fake".getBytes()
                );

        when(productServiceImpl.updateProduct(any(), any()))
                .thenReturn(dto);

        mockMvc.perform(
                        multipart("/api/admin/productsUpdate")
                                .file(productJson)
                                .file(image)
                                .with(request -> {    // ⭐ required hack for PUT + multipart
                                    request.setMethod("PUT");
                                    return request;
                                })
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Laptop"));
    }
}
