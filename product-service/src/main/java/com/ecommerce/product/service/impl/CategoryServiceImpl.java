package com.ecommerce.product.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ecommerce.product.payloads.CommonResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.exception.CategoryNotFoundException;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommonResponse addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category = categoryRepository.save(category);
        categoryDto = modelMapper.map(category, categoryDto.getClass());
        return CommonResponse.builder().message("Category created successfully!").data(categoryDto).httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public CommonResponse getAllCategory() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = Arrays.asList(modelMapper.map(all, CategoryDto[].class));
        return CommonResponse.builder().message("All Categories!").data(categoryDtos).httpStatus(HttpStatus.OK).build();
    }

    @Override
    public CommonResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("category not found with id " + id));
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return CommonResponse.builder().message("Category with id :"+id).data(categoryDto).httpStatus(HttpStatus.OK).build();
    }

}
