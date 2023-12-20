package com.ecommerce.product.controller;

import java.util.List;

import com.ecommerce.product.payloads.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.service.CategoryService;

@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")
	ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto){
		CommonResponse commonResponse = categoryService.addCategory(categoryDto);
		return ResponseEntity.created(null).body(commonResponse);
	}
	
	@GetMapping("/all")
	ResponseEntity<?> getAllCategories(){
		CommonResponse commonResponse = categoryService.getAllCategory();
		return ResponseEntity.ok(commonResponse);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getCategoryById(@PathVariable long id){
		CommonResponse commonResponse = categoryService.getCategoryById(id);
		return ResponseEntity.ok(commonResponse);
	}
}
