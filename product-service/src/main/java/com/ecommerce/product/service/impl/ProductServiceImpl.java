package com.ecommerce.product.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.ProductImages;
import com.ecommerce.product.payloads.CommonResponse;
import com.ecommerce.product.repository.ProductImagesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.product.dto.AddProductRequest;
import com.ecommerce.product.dto.ProductResponseDTO;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.CategoryNotFoundException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.FileService;
import com.ecommerce.product.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final CategoryRepository categoryRepository;

	@Autowired
	private final ModelMapper modelMapper;

	@Autowired
	private final FileService fileService;
	
	@Autowired
	private final HttpServletResponse response;

	@Autowired
	private final ProductImagesRepository productImagesRepository;

	private final String path  =System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator+"static"+File.separator+"products";

	@Override
	public CommonResponse addProduct(AddProductRequest productDto) throws IOException {
		ProductResponseDTO productResponseDTO = null;
		final Integer categoryId = productDto.getCategory_id();
		Category category = categoryRepository.findById(productDto.getCategory_id().longValue()).orElseThrow(
				() -> new CategoryNotFoundException("category not found with id :" + categoryId));
		Product product = modelMapper.map(productDto, Product.class);
		product.setCategory(category);

		if (!productDto.getThumbnailUrl().isEmpty()) {
			try {
				String fileName = fileService.uploadImage(path, productDto.getThumbnailUrl());
				String thumbnailUrl = buildImageUrl(fileName);
				product.setThumbnailUrl(thumbnailUrl);
				product = productRepository.save(product);
				productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
				return CommonResponse.builder().message("Product Added Succssfully!").data(productResponseDTO).httpStatus(HttpStatus.CREATED).build();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("Please select a product image ");

	}

	private static String buildImageUrl(String fileName) {
		String thumbnailUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/product/get-image/")
				.path(fileName).toUriString();
		return thumbnailUrl;
	}

	@Override
	public CommonResponse getAllProducts() {
		List<Product> all = productRepository.findAll();
		List<ProductResponseDTO> productResponseDTOS = Arrays.asList(modelMapper.map(all, ProductResponseDTO[].class));
		productResponseDTOS.forEach(product -> {
			List<String> imageUrls = productImagesRepository.findByProductId(product.getId());
			product.setSideImages(imageUrls);
		});
		return CommonResponse.builder().message("All Products With").data(productResponseDTOS).httpStatus(HttpStatus.OK).build();
	}

	@Override
	public CommonResponse getProductById(Integer id) {
		Product prodcut = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("product not found with id " + id));
		ProductResponseDTO productResponseDTO = modelMapper.map(prodcut, ProductResponseDTO.class);
		List<String> imageUrls = productImagesRepository.findByProductId(productResponseDTO.getId());
		productResponseDTO.setSideImages(imageUrls);
		return CommonResponse.builder().message("Product with id : "+id).data(productResponseDTO).httpStatus(HttpStatus.OK).build();
	}

	@Override
	public CommonResponse uploadSideImages(List<MultipartFile> images, Integer product_id) {
		Product product = productRepository.findById(product_id).orElseThrow(() -> new ProductNotFoundException("Product does not exist with id : " + product_id));
		ArrayList<String> sideImageUrls = new ArrayList<>();
		images.stream().forEach(image->{
			if (!image.isEmpty()){
				try {
					String fileName = fileService.uploadImage(path, image);
					String imageUrl = buildImageUrl(fileName);
					ProductImages productImages = ProductImages.builder().imageUrl(imageUrl).product(product).build();
					ProductImages save = productImagesRepository.save(productImages);
					sideImageUrls.add(save.getImageUrl());

				} catch (IOException e) {
					throw new RuntimeException("File not found Exception");
				}
			};
		});
		ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
		productResponseDTO.setSideImages(sideImageUrls);

		return CommonResponse.builder().message("Side images uploaded succesfully!").data(productResponseDTO).httpStatus(HttpStatus.OK).build();
	}

	@Override
	public void downloadImage(String imageName) {
		try {
			InputStream resource = fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    StreamUtils.copy(resource, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
