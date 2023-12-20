package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.payloads.CommonResponse;

public interface CategoryService {

	CommonResponse addCategory(CategoryDto categoryDto);
	
	CommonResponse getAllCategory();
	
	CommonResponse getCategoryById(Long id);
}
