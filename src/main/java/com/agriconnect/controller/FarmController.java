package com.agriconnect.controller;

import com.agriconnect.dto.CreateFarmRequest;
import com.agriconnect.dto.FarmResponse;
import com.agriconnect.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping
    public ResponseEntity<FarmResponse> createFarm(
            @RequestBody CreateFarmRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(farmService.createFarm(request));
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getMyFarms() {

        return ResponseEntity.ok(farmService.getMyFarms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponse> getFarmById(
            @PathVariable Long id) {

        return ResponseEntity.ok(farmService.getFarmById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponse> updateFarm(
            @PathVariable Long id,
            @RequestBody CreateFarmRequest request) {

        return ResponseEntity.ok(
                farmService.updateFarm(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(
            @PathVariable Long id) {

        farmService.deleteFarm(id);

        return ResponseEntity.noContent().build();
    }
}