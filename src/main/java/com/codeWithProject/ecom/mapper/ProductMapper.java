package com.codeWithProject.ecom.mapper;

import org.springframework.stereotype.Component;

import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.entity.Product;

@Component
public class ProductMapper {

	public ProductDto  toDto(Product product) {
			ProductDto dto = new ProductDto();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setPrice(product.getPrice());
			dto.setImageUrl(product.getImageUrl());
			dto.setDescription(product.getDescription());
			return dto;
	}
	
	
	public Product toEntity(ProductDto dto) {
		Product product = new Product();
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setImageUrl(dto.getImageUrl());
		product.setDescription(dto.getDescription());
		return product;
	}
	
}
