package com.agriconnect.service.impl;

import com.agriconnect.dto.CreateProductRequest;
import com.agriconnect.dto.ProductResponse;
import com.agriconnect.entity.*;
import com.agriconnect.repository.*;
import com.agriconnect.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;
    private final UserRepository userRepository;

    @Override
    public ProductResponse createProduct(
            CreateProductRequest request) {

        User farmer = getLoggedInUser();

        Farm farm = farmRepository.findByIdAndFarmerId(
                        request.getFarmId(),
                        farmer.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Farm not found"));

        Crop crop = cropRepository.findById(
                        request.getCropId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found"));

        Product product = Product.builder()
                .farm(farm)
                .farmer(farmer)
                .crop(crop)
                .quantity(request.getQuantity())
                .availableQuantity(request.getQuantity())
                .unit(request.getUnit())
                .pricePerUnit(request.getPricePerUnit())
                .description(request.getDescription())
                .status(ProductStatus.AVAILABLE)
                .build();

        return mapToResponse(
                productRepository.save(product));
    }

    @Override
    public List<ProductResponse> getMyProducts() {

        User farmer = getLoggedInUser();

        return productRepository.findByFarmerId(
                        farmer.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        User farmer = getLoggedInUser();

        Product product =
                productRepository.findByIdAndFarmerId(
                                id,
                                farmer.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(
            Long id,
            CreateProductRequest request) {

        User farmer = getLoggedInUser();

        Product product =
                productRepository.findByIdAndFarmerId(
                                id,
                                farmer.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        product.setQuantity(request.getQuantity());
        product.setAvailableQuantity(
                request.getQuantity());

        product.setUnit(request.getUnit());
        product.setPricePerUnit(
                request.getPricePerUnit());

        product.setDescription(
                request.getDescription());

        return mapToResponse(
                productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {

        User farmer = getLoggedInUser();

        Product product =
                productRepository.findByIdAndFarmerId(
                                id,
                                farmer.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        productRepository.delete(product);
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }

    private ProductResponse mapToResponse(
            Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .farmId(product.getFarm().getId())
                .farmName(product.getFarm().getFarmName())
                .cropId(product.getCrop().getId())
                .cropName(product.getCrop().getName())
                .quantity(product.getQuantity())
                .quantityAvailable(
                        product.getAvailableQuantity())
                .unit(product.getUnit())
                .pricePerUnit(
                        product.getPricePerUnit())
                .description(
                        product.getDescription())
                .status(
                        product.getStatus().name())
                .build();
    }
}