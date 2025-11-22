package com.codeWithProject.ecom.serviceImpl;



import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.entity.Category;
import com.codeWithProject.ecom.entity.Product;
import com.codeWithProject.ecom.exception.FileValidationException;
import com.codeWithProject.ecom.exception.ResourceNotFoundException;
import com.codeWithProject.ecom.mapper.ProductMapper;
import com.codeWithProject.ecom.repository.CategoryRepository;
import com.codeWithProject.ecom.repository.ProductRepository;
import com.codeWithProject.ecom.service.ProductService;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	
	private final CategoryRepository categoryRepository;
	
	private final ProductMapper productMapper;
	
	
	public ProductDto addProduct(ProductDto productDto,MultipartFile img) {
		log.info("Creating product with categry id: {}",productDto.getCategoryId());
		
		Category category =  categoryRepository.findById(productDto.getCategoryId())
				.orElseThrow(()-> new ResourceNotFoundException("Category not found with Id"));		
		
			String imageUrl = null;
		
			if(img != null && !img.isEmpty()) {
				imageUrl = saveImage(img);
			}
		
			Product product = new Product();
			product.setName(productDto.getName());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());
			product.setImageUrl(imageUrl);
			product.setCategory(category);
		
			
			
			Product savedProduct = productRepository.save(product);
			
			productDto.setId(savedProduct.getId());
			return productDto;
	}
	
	
		public String saveImage(MultipartFile file) {
			try {
				
					if(file.isEmpty()) {
						throw new FileValidationException("File cannot be empty");
					}
					
					String type = file.getContentType();
					if((type.equals("images/jpeg") || type.equals("images/png"))) {
						throw new  FileValidationException("Only JPG or PNG allowed");
					}
					
					if(file.getSize() > (5 * 1024 * 1024)) {
						throw new FileValidationException("File too large (max 5MB)");
					}
				
				
				
				String folder = "uploads/";
				Files.createDirectories(Paths.get(folder));
				
				String filePath = folder + System.currentTimeMillis() +"_"+file.getOriginalFilename();
				Path path = Paths.get(filePath);
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            return filePath;
				
			}catch (IOException ex) {
				throw new RuntimeException("Image upload failed");
			}
		}
		
		public void deleteFile(String filename) {
			try {
				Path path = Paths.get("uploads/"+filename);
				Files.deleteIfExists(path);
			}catch(Exception ex) {
				
			}
		}


		@Override
		public List<ProductDto> getAllProduct() {
			List<Product> product = productRepository.findAll();
			return product.stream().map(productMapper::toDto).collect(Collectors.toList());
		}


		@Override
		public List<ProductDto> getAllProductByName(String name) {
			List<Product> product = productRepository.findAllByName(name);
			return product.stream().map(productMapper::toDto).collect(Collectors.toList());
		}


		@Override
		public void deleteProduct(Long id) {
			log.info("Update product id: {}",id);
			Product product = productRepository.findById(id)
					.orElseThrow(()->new ResourceNotFoundException("Product is not available"));
			
			productRepository.delete(product);
		}


		@Override
		public ProductDto updateProduct(ProductDto productDto, MultipartFile img) {
			Product product = productRepository.findById(productDto.getId())
					.orElseThrow(()-> new ResourceNotFoundException("Product is not available"));
			
			product.setId(productDto.getId());
			product.setName(productDto.getName());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());
			
			if(!img.isEmpty() && img !=null) {
				if(product.getImageUrl() != null) {
					deleteFile(product.getImageUrl());
				}
				
				String newImagePath =  saveImage(img);
				product.setImageUrl(newImagePath);
			}			
			
			productRepository.save(product);
			
			return productMapper.toDto(product);
		}
		
		
		
	
}
