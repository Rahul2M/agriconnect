package com.agriconnect.dto;



import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FarmResponse {

    private Long id;
    private String farmName;
    private BigDecimal landArea;
    private String landUnit;
    private String soilType;
    private String waterSource;
    private BigDecimal latitude;
    private BigDecimal longitude;
}