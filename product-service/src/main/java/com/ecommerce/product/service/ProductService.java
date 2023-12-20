package com.ecommerce.product.service;

import java.io.IOException;
import java.util.List;

import com.ecommerce.product.dto.AddProductRequest;
import com.ecommerce.product.dto.ProductResponseDTO;
import com.ecommerce.product.payloads.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

	CommonResponse addProduct(AddProductRequest productDto) throws IOException;
	
	CommonResponse getAllProducts();
	
	CommonResponse getProductById(Integer id);

	CommonResponse uploadSideImages(List<MultipartFile> images, Integer product_id);
	
	void downloadImage(String imageName);
}
