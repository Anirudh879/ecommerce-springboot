package com.codeWithProject.ecom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeWithProject.ecom.dto.ProductDto;
import com.codeWithProject.ecom.helper.ApiResponse;
import com.codeWithProject.ecom.helper.ResponseHelper;
import com.codeWithProject.ecom.serviceImpl.ProductServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/admin")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductServiceImpl productServiceImpl;
	
	@PostMapping(value="/products",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<ProductDto>> addProduct( 
			@RequestPart("product") @Valid ProductDto productDto, 
			@RequestPart(value = "image", required = false) MultipartFile image ){
			log.info("add product for category id:{}",productDto.getCategoryId());
			
			ProductDto productAdded = productServiceImpl.addProduct(productDto, image);
			log.info("Product added successfully with id: {}",productAdded.getId());
			
			ApiResponse<ProductDto> response = ResponseHelper.success("Product added Successfully", productAdded);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);	
	}
	
	@GetMapping("/getProducts")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts(){
		log.info("fetch all products");
		List<ProductDto> products = productServiceImpl.getAllProduct();
		ApiResponse<List<ProductDto>> response = ResponseHelper.success("all products fetched", products);
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	
	@GetMapping("/product/{name}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProductsByName(@PathVariable String name){
		log.info("fetch all products");
		List<ProductDto> products = productServiceImpl.getAllProductByName(name);
		ApiResponse<List<ProductDto>> response = ResponseHelper.success("all products fetched", products);
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){
		log.info("delete product with id : {}"+id);
		productServiceImpl.deleteProduct(id);
		ApiResponse<Void> response = ResponseHelper.success("Product deleted", null);
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	
	@PutMapping("/productsUpdate")
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
			@RequestPart("product") @Valid ProductDto productDto, 
			@RequestPart(value = "image", required = false) MultipartFile image){
			log.info("update product");
			ProductDto product = productServiceImpl.updateProduct(productDto, image);
			ApiResponse<ProductDto> response = ResponseHelper.success("product updated", product);
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
