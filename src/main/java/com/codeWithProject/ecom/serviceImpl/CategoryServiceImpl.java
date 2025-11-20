package com.codeWithProject.ecom.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeWithProject.ecom.dto.CategoryDto;
import com.codeWithProject.ecom.dto.CategoryWithProductDto;
import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.entity.Category;
import com.codeWithProject.ecom.entity.Product;
import com.codeWithProject.ecom.exception.ResourceAlreadyExistsException;
import com.codeWithProject.ecom.exception.ResourceNotFoundException;
import com.codeWithProject.ecom.projection.CategoryProductProjection;
import com.codeWithProject.ecom.repository.CategoryRepository;
import com.codeWithProject.ecom.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	    
	    log.info("Creating Category with name: {}", categoryDto.getName());
	    
	    if (categoryRepository.existsByName(categoryDto.getName())) {
	        log.warn("Category '{}' already exists", categoryDto.getName());
	        throw new ResourceAlreadyExistsException("Category already exists");
	    }

	    Category category = new Category();
	    category.setName(categoryDto.getName());
	    category.setDescription(categoryDto.getDescription());

	    Category createdCategory = categoryRepository.save(category);

	    categoryDto.setId(createdCategory.getId());
	    categoryDto.setName(createdCategory.getName());
	    categoryDto.setDescription(createdCategory.getDescription());

	    return categoryDto;
	}
	
	@Override
	public List<CategoryDto> getAllCategory(){
		 log.info("get all Category for a user: {}");
		 
		 List<Category> categories = categoryRepository.findAll();
		 List<CategoryDto> dtoList = new ArrayList<>();
		 
		 for(Category category: categories) {
			 CategoryDto dto = new CategoryDto();
			 dto.setId(category.getId());
			 dto.setName(category.getName());
			 dto.setDescription(category.getDescription());
			 
			 dtoList.add(dto);
		 }
		 
		 return dtoList;
		 
	}


	public CategoryWithProductDto getCategoryWithProducts(Long id) {
		
		log.info("fetch Category with id: {}", id);
		List<CategoryProductProjection> rows =  categoryRepository.findByIdWithProducts(id);
				
		if(rows.isEmpty()) {
			throw new ResourceNotFoundException("Category not available");
		}
		
		CategoryWithProductDto dto =  new CategoryWithProductDto();
		dto.setId(rows.get(0).getCategoryId());
		dto.setName(rows.get(0).getCategoryName());
		
		List<ProductDto> products = rows.stream()
				.map(row -> {
					ProductDto p = new ProductDto();
					p.setId(row.getProductId());
					p.setName(row.getProductName());
					p.setPrice(row.getProductPrice());
					return p;
					
				}).collect(Collectors.toList());
		
		dto.setProducts(products);
		
		return dto;
	}

	@Override
	public void deleteCategory(Long id) {
		log.info("delete Category with id: {}", id);
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category not available"));
		
		categoryRepository.delete(category);
		
		
	}

	@Override
	public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
		
		Category category = categoryRepository.findById(id)
							.orElseThrow(()->new ResourceNotFoundException("Category not available"));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		Category updatedCategory = categoryRepository.save(category);
		
		categoryDto.setName(updatedCategory.getName());
		categoryDto.setDescription(updatedCategory.getDescription());
		return categoryDto;
	}
	
	
}
