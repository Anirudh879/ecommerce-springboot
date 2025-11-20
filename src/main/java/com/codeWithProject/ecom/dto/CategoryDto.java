package com.codeWithProject.ecom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
	private Long id;
	
	@NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;
	

    @Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;
}
