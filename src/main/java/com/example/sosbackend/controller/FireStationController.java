package com.example.sosbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sosbackend.dto.EmergencyServiceResponseDTO;
import com.example.sosbackend.exceptions.ResourceNotFoundException;
import com.example.sosbackend.model.EmergencyServicesModel;
import com.example.sosbackend.response.ApiResponse;
import com.example.sosbackend.service.FireStationService;
import com.example.sosbackend.util.ResponseUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

/**
 * Handles API requests for fire station operations.
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Fire Stations", description = "APIs for fire station routes")
public class FireStationController {

    private final FireStationService fireStationService;

    /**
     * Initializes with FireStationService.
     */
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * Fetches nearby fire stations within a radius.
     * @throws ResourceNotFoundException if no stations found.
     */
    @GetMapping("/fire-stations")
    @Operation(summary = "Get fire stations within a coordinate", description = "Fetch fire stations within a coordinate using a radius")
    public ResponseEntity<ApiResponse<List<EmergencyServiceResponseDTO>>> findNearbyFireStations(
        @RequestParam(required = true) @DecimalMin(value = "-180.0", message = "longitude must be greater than or equal to -180.0") @DecimalMax(value = "180.0", message = "longitude must be less than or equal to 180.0") @Parameter(description = "Longitude of the center point") double longitude,
        @RequestParam(required = true) @DecimalMin(value = "-90.0", message = "latitude must be greater than or equal -90.0") @DecimalMax(value = "90.0", message = "latitude must be less than or equal to 90.0") @Parameter(description = "Latitude of the center point") double latitude,
        @RequestParam(required = true) @Positive(message = "radius must be positive") @Parameter(description = "Search radius in meters") double radius,
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1) @Parameter(description = "Optional resource page number") int page,
        @RequestParam(required = false, defaultValue = "5") @Min(value = 1) @Max(value = 10) @Parameter(description = "Optional resource limit number") int limit,
        HttpServletRequest request) throws ResourceNotFoundException {

        List<EmergencyServicesModel> fireStations = this.fireStationService
            .findNearbyFireStations(longitude, latitude, radius, page, limit);

        if (fireStations.size() == 0) {
            throw new ResourceNotFoundException("No nearby fire stations found for this coordinate");
        }

        return ResponseEntity.ok(ResponseUtil.response(EmergencyServiceResponseDTO.from(fireStations), page, limit,
            "Nearby fire stations successfully fetched", request.getRequestURI()));
    }
}