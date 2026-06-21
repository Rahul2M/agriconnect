package com.agriconnect.service;


import java.util.List;

import com.agriconnect.dto.CreateFarmRequest;
import com.agriconnect.dto.FarmResponse;

public interface FarmService {

    FarmResponse createFarm(CreateFarmRequest request);

    List<FarmResponse> getMyFarms();

    FarmResponse getFarmById(Long id);

    FarmResponse updateFarm(Long id, CreateFarmRequest request);

    void deleteFarm(Long id);
}
