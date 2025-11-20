package com.codeWithProject.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codeWithProject.ecom.entity.Category;
import com.codeWithProject.ecom.projection.CategoryProductProjection;

public interface CategoryRepository extends JpaRepository<Category,Long>{
	boolean existsByName(String name);
	
	@Query(value  =  """
		    SELECT 
	        c.id AS categoryId,
	        c.name AS categoryName,
	        p.id AS productId,
	        p.name AS productName,
	        p.price AS productPrice
	    FROM category c 
	    LEFT JOIN product p ON c.id = p.category_id
	    WHERE c.id = :id
	    """, nativeQuery = true)
	List<CategoryProductProjection> findByIdWithProducts(@Param("id") Long id);
}
