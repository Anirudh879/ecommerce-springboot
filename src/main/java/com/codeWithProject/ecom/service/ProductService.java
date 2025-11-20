package com.codeWithProject.ecom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.codeWithProject.ecom.dto.ProductDto;

public interface ProductService {
	ProductDto addProduct(ProductDto productDto,MultipartFile img);  
	
	String saveImage(MultipartFile file);
	
	List<ProductDto> getAllProduct();
	
	List<ProductDto> getAllProductByName(String name);
	
	void deleteProduct(Long id);
	
	ProductDto updateProduct(ProductDto productDto,MultipartFile img);
}
