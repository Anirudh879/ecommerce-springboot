package com.codeWithProject.ecom.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProject.ecom.dto.CategoryDto;
import com.codeWithProject.ecom.dto.CategoryWithProductDto;
import com.codeWithProject.ecom.helper.ApiResponse;
import com.codeWithProject.ecom.helper.ResponseHelper;
import com.codeWithProject.ecom.serviceImpl.CategoryServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/admin")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {
	
	
	private final CategoryServiceImpl categoryServiceImpl;
    private final ResponseHelper responseHelper;  // inject helper
	
	//create categories
	@PostMapping("/categories")
	public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		log.info("create category for name:{}",categoryDto.getName());
		
		CategoryDto categoryCreated = categoryServiceImpl.createCategory(categoryDto);
		log.info("Category created successfully with id: {}",categoryCreated.getId());
		
		ApiResponse<CategoryDto> response = responseHelper.success("Category created successfully", categoryCreated);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	// get All categories
	@GetMapping("/getCategories")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategory(){
		log.info("fetch all category for a user:{}");
		
		List<CategoryDto>  categories = categoryServiceImpl.getAllCategory();
		
		ApiResponse<List<CategoryDto>> response = responseHelper.success("Category fetched successfully", categories);
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	} 
	
	//get category with all products
	@GetMapping("/{id}/getCategoryWithProducts")
	public ResponseEntity<ApiResponse<CategoryWithProductDto>> getCategoryWithProduct(@PathVariable Long id){
		log.info("fetch category with all products for a user:{}");
		
		CategoryWithProductDto dto = categoryServiceImpl.getCategoryWithProducts(id);
		ApiResponse<CategoryWithProductDto> response = responseHelper.success("Category with product fetch successfully", dto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	//update category
	@PutMapping("/categories/{id}")
	public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Long id){
		log.info("update category with id for user:{}");
		
		CategoryDto dto = categoryServiceImpl.updateCategory(id, categoryDto);
		ApiResponse<CategoryDto> response = responseHelper.success("category updated", dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
}
