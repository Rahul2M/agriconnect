package com.agriconnect.service.impl;



import com.agriconnect.dto.CreateFarmRequest;
import com.agriconnect.dto.FarmResponse;
import com.agriconnect.entity.Farm;
import com.agriconnect.entity.User;
import com.agriconnect.repository.FarmRepository;
import com.agriconnect.repository.UserRepository;
import com.agriconnect.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    @Override
    public FarmResponse createFarm(CreateFarmRequest request) {

        User farmer = getLoggedInUser();

        Farm farm = Farm.builder()
                .farmName(request.getFarmName())
                .landArea(request.getLandArea())
                .landUnit(request.getLandUnit())
                .soilType(request.getSoilType())
                .waterSource(request.getWaterSource())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .farmer(farmer)
                .build();

        return mapToResponse(farmRepository.save(farm));
    }

    @Override
    public List<FarmResponse> getMyFarms() {

        User farmer = getLoggedInUser();

        return farmRepository.findByFarmerId(farmer.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public FarmResponse getFarmById(Long id) {

        User farmer = getLoggedInUser();

        Farm farm = farmRepository.findByIdAndFarmerId(id, farmer.getId())
                .orElseThrow(() -> new RuntimeException("Farm not found"));

        return mapToResponse(farm);
    }

    @Override
    public FarmResponse updateFarm(Long id, CreateFarmRequest request) {

        User farmer = getLoggedInUser();

        Farm farm = farmRepository.findByIdAndFarmerId(id, farmer.getId())
                .orElseThrow(() -> new RuntimeException("Farm not found"));

        farm.setFarmName(request.getFarmName());
        farm.setLandArea(request.getLandArea());
        farm.setLandUnit(request.getLandUnit());
        farm.setSoilType(request.getSoilType());
        farm.setWaterSource(request.getWaterSource());
        farm.setLatitude(request.getLatitude());
        farm.setLongitude(request.getLongitude());

        return mapToResponse(farmRepository.save(farm));
    }

    @Override
    public void deleteFarm(Long id) {

        User farmer = getLoggedInUser();

        Farm farm = farmRepository.findByIdAndFarmerId(id, farmer.getId())
                .orElseThrow(() -> new RuntimeException("Farm not found"));

        farmRepository.delete(farm);
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private FarmResponse mapToResponse(Farm farm) {

        return FarmResponse.builder()
                .id(farm.getId())
                .farmName(farm.getFarmName())
                .landArea(farm.getLandArea())
                .landUnit(farm.getLandUnit())
                .soilType(farm.getSoilType())
                .waterSource(farm.getWaterSource())
                .latitude(farm.getLatitude())
                .longitude(farm.getLongitude())
                .build();
    }
}