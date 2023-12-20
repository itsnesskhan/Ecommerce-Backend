package com.ecommerce.product.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

	private String name;

	private String description;

	private long disPrice;

	private long actPrice;

	private long availableStock;

	private MultipartFile thumbnailUrl;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Integer category_id;
}
