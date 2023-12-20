package com.ecommerce.product.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description" ,nullable = false)
	private String description;
	
	@Column(name = "discounted_price", nullable = false)
	private long disPrice;
	
	@Column(name = "actual_price", nullable = false)
	private long actPrice;
	
	@Column(name = "available_in_stock", nullable = false)
	private long availbleStock;
	
	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@JoinColumn(name = "category_id", nullable = false)
	@ManyToOne
	private Category category;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
