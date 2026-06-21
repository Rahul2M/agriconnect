package com.agriconnect.repository;

import com.agriconnect.entity.Product;
import com.agriconnect.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByFarmerId(Long farmerId);

    List<Product> findByCropId(Long cropId);

    List<Product> findByStatus(ProductStatus status);

    Optional<Product> findByIdAndFarmerId(
            Long productId,
            Long farmerId
    );
}