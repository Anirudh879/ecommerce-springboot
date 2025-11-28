package com.codeWithProject.ecom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.entity.Category;
import com.codeWithProject.ecom.entity.Product;
import com.codeWithProject.ecom.exception.FileValidationException;
import com.codeWithProject.ecom.exception.ResourceNotFoundException;
import com.codeWithProject.ecom.mapper.ProductMapper;
import com.codeWithProject.ecom.repository.CategoryRepository;
import com.codeWithProject.ecom.repository.ProductRepository;
import com.codeWithProject.ecom.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private ProductMapper productMapper;
	
	 @Mock
	 private MultipartFile file;
	 
	 @InjectMocks
	 private ProductServiceImpl productService;
	 
	 private Product product;
	 private Category category;
	 private ProductDto productDto;
	 
	 
	 @BeforeEach
	 void setUp() {
		 category = new Category();
		 category.setId(1L);
		 category.setName("Electronics");
		 category.setDescription("All Electronic devices");
		 
		 
		 product = new Product();
		 product.setId(10L);
		 product.setName("Phone");
		 product.setPrice(new BigDecimal(50000.12));
		 product.setDescription("Flagship Phone");
		 product.setCategory(category);
		 
		 productDto = new ProductDto();
	        productDto.setId(10L);
	        productDto.setName("Phone");
	        productDto.setPrice(new BigDecimal(50000.12)	);
	        productDto.setDescription("Flagship Phone");
	        productDto.setCategoryId(1L);
	 }
	
	// ---------------------------------------------------------------
    // addProduct()
    // ---------------------------------------------------------------
	 @Test
	 void addProduct_success_withoutImage() {
		 when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		 when(productRepository.save(any(Product.class))).thenReturn(product);
		 
		 ProductDto result = productService.addProduct(productDto, null);
		 
		 assertEquals(10L, result.getId());
		 verify(productRepository,times(1)).save(any());
		 
	 }
	 
	 @Test
	 void addProduct_CategoryNotFound_shouldThrow() {
		 when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
		 assertThrows(ResourceNotFoundException.class, ()->productService.addProduct(productDto, null));
	 }
	 
	 
	 @Test
	 void saveImage_emptyFile_shouldThrow() {
		 when(file.isEmpty()).thenReturn(true);
		 assertThrows(FileValidationException.class, ()->productService.saveImage(file));
	 }
	 
	 @Test
	 void saveImage_invalidType_shouldThrow() {
		 when(file.isEmpty()).thenReturn(false);
		 when(file.getContentType()).thenReturn("images/jpeg");
		 assertThrows(FileValidationException.class, ()->productService.saveImage(file));
	 }

    @Test
    void saveImage_largeFile_shouldThrow() {
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(6L * 1024 * 1024);

        assertThrows(FileValidationException.class, () -> productService.saveImage(file));
    }

    @Test
    void saveImage_success() throws Exception {
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(1024L);
        when(file.getInputStream()).thenReturn(InputStream.nullInputStream());
        when(file.getOriginalFilename()).thenReturn("test.png");

        String path = productService.saveImage(file);

        assertTrue(path.contains("uploads/"));
    }
	 
	 @Test
	 void getAllProducts_success() {
		 when(productRepository.findAll()).thenReturn(List.of(product));
		 when(productMapper.toDto(product)).thenReturn(productDto);

		 List<ProductDto> result = productService.getAllProduct();
		 assertEquals(1, result.size());
		 assertEquals("Phone", result.get(0).getName());
	 }
	 
	 @Test
	 void getAllProductsByName_success() {
		 when(productRepository.findAllByName("Phone")).thenReturn(List.of(product));
		 when(productMapper.toDto(product)).thenReturn(productDto);
		 
		 List<ProductDto> result = productService.getAllProductByName("Phone");
		 assertEquals(1, result.size());
	 }
	 
	 
	 @Test
	 void deleteProduct_success() {
		 when(productRepository.findById(10L)).thenReturn(Optional.of(product));
		 productService.deleteProduct(10L);
		 verify(productRepository, times(1)).delete(product);
	 }
	 
	 @Test
	 void deleteProduct_notFound_shouldThrow() {
		 when(productRepository.findById(10L)).thenReturn(Optional.empty());
		 assertThrows(ResourceNotFoundException.class, ()->productService.deleteProduct(10L));
	 }	 
	 
	 @Test
	 void updateProduct_success_withoutImage() {
		 when(productRepository.findById(10L)).thenReturn(Optional.of(product));
		 when(productRepository.save(any())).thenReturn(product);
		 when(productMapper.toDto(product)).thenReturn(productDto);
		  ProductDto result = productService.updateProduct(productDto, null);
		  assertEquals("Phone", result.getName());
		 
	 }
	 
	 @Test
	 void updateProduct_notFound_shouldThrow() {
		 when(productRepository.findById(10L)).thenReturn(Optional.empty());
		 
		 assertThrows(ResourceNotFoundException.class,
	                () -> productService.updateProduct(productDto, null));
	 }
}
