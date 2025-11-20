package com.codeWithProject.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="product")
public class Product {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(nullable = false)
		private String name;
		
		@Column(nullable = false)
		private BigDecimal price;
		
		@Lob
		@Column(length = 1000)
		private String description;
		
		@Column(length = 500)
		private String imageUrl;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "category_id", nullable = false)
		@OnDelete(action = OnDeleteAction.CASCADE)
		@JsonIgnore
		private Category category;
		
}
