package com.ecommerce.product.repository;

import com.ecommerce.product.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {

    @Query(value = "SELECT image_url FROM product_images where product_id =:product_id", nativeQuery = true)
    List<String> findByProductId(Long product_id);
}
