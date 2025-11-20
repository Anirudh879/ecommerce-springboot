package com.codeWithProject.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeWithProject.ecom.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findAllByName(String name);

}
