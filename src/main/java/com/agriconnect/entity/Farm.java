package com.agriconnect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "farms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @Column(name = "farm_name")
    private String farmName;

    @Column(name = "land_area", nullable = false)
    private BigDecimal landArea;

    @Column(name = "land_unit", nullable = false)
    private String landUnit;

    @Column(name = "soil_type")
    private String soilType;

    @Column(name = "water_source")
    private String waterSource;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "farm")
    private List<Product> products;
}