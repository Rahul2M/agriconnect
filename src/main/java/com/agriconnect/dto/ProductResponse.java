package com.agriconnect.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private Long id;

    private Long farmId;
    private String farmName;

    private Long cropId;
    private String cropName;

    private BigDecimal quantity;
    private BigDecimal quantityAvailable;

    private String unit;
    private BigDecimal pricePerUnit;

    private String description;
    private String status;
}