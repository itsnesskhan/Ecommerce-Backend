package com.ecommerce.product.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
	
	private long id;
	private String name;
	@JsonProperty(namespace = "description")
	private String description;
	@JsonProperty(namespace = "discounted_price")
	private long disPrice;
	@JsonProperty(namespace = "actual_price")
	private long actPrice;
	@JsonProperty(namespace = "available_in_stock")
	private long availbleStock;
	@JsonProperty(namespace = "thumbnail_image")
	private String thumbnailUrl;
	@JsonProperty("side_images")
	private List<String> sideImages;
	@JsonProperty(namespace = "category", access = JsonProperty.Access.WRITE_ONLY)
	private Integer category_id;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private CategoryDto category;
}
