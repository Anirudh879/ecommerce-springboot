package com.codeWithProject.ecom.service;

import java.util.List;

import com.codeWithProject.ecom.dto.CategoryDto;
import com.codeWithProject.ecom.dto.CategoryWithProductDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategory();
	
	CategoryWithProductDto getCategoryWithProducts(Long id);
	
	void deleteCategory(Long id);
	
	CategoryDto updateCategory(Long id,CategoryDto categoryDto);
	
}
