package com.agriconnect.service;



import java.util.List;

import com.agriconnect.dto.CreateProductRequest;
import com.agriconnect.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    List<ProductResponse> getMyProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(
            Long id,
            CreateProductRequest request
    );

    void deleteProduct(Long id);
}
