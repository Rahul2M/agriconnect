package com.agriconnect.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateProductRequest {

    private Long farmId;
    private Long cropId;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal pricePerUnit;
    private String description;
}
