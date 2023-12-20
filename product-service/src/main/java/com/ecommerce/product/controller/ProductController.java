package com.ecommerce.product.controller;

import com.ecommerce.product.dto.AddProductRequest;
import com.ecommerce.product.payloads.CommonResponse;
import com.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/product")
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@PostMapping(path = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CommonResponse> addProduct(@ModelAttribute AddProductRequest productDtoRequest) throws IOException {
		CommonResponse commonResponse = productService.addProduct(productDtoRequest);
		return ResponseEntity.created(null).body(commonResponse);
	}
	
	@GetMapping("/all")
	public ResponseEntity<CommonResponse> getAllProducts(){
		CommonResponse commonResponse = productService.getAllProducts();
		return ResponseEntity.ok(commonResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CommonResponse> getProductById(@PathVariable Integer id){
		CommonResponse commonResponse = productService.getProductById(id);
		return ResponseEntity.ok(commonResponse);
	}
	@PostMapping(path = "{id}/upload-side-images/",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public  ResponseEntity<?> uploadSideImages(@ModelAttribute List<MultipartFile> images,
											   @PathVariable Integer id ){
		CommonResponse commonResponse = productService.uploadSideImages(images, id);
		return  ResponseEntity.ok(commonResponse);
	}
	@GetMapping(path = "/get-image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getProfileImage(@PathVariable String imageName){
		productService.downloadImage(imageName);
	}
	

}
