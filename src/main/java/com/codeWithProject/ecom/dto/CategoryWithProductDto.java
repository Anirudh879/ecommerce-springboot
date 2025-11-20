package com.codeWithProject.ecom.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryWithProductDto {
	private Long id;
	
	@NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;
	
	List<ProductDto> products;
}
