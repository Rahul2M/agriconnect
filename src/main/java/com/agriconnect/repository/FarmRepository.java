package com.agriconnect.repository;

import com.agriconnect.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm, Long> {

    List<Farm> findByFarmerId(Long farmerId);

    Optional<Farm> findByIdAndFarmerId(Long farmId, Long farmerId);
    
}