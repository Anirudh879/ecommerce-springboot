package com.codeWithProject.ecom.mapper;

import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.entity.Product;

public class ProductMapper {

	public static ProductDto  toDto(Product product) {
			ProductDto dto = new ProductDto();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setPrice(product.getPrice());
			dto.setImageUrl(product.getImageUrl());
			dto.setDescription(product.getDescription());
			return dto;
	}
	
	
	public static Product toEntity(ProductDto dto) {
		Product product = new Product();
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setImageUrl(dto.getImageUrl());
		product.setDescription(product.getDescription());
		return product;
	}
	
}
