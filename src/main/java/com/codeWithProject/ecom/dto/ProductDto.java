package com.codeWithProject.ecom.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDto {
	private Long id;
	
	@NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;
	
	@Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;
	
	 @PositiveOrZero(message = "Price must be zero or positive")
	 private BigDecimal  price;
	 
	 private String imageUrl; 
	
	@NotNull(message = "Category ID is required")
	private Long categoryId;
}
