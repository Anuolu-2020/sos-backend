package com.example.sosbackend.controller.interfaces;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.sosbackend.dto.CreateEmergencyServiceRequest;
import com.example.sosbackend.dto.EmergencyServiceResponseDTO;
import com.example.sosbackend.exceptions.ResourceNotFoundException;
import com.example.sosbackend.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;

@Tag(name = "Emergency Services", description = "Apis for emergency services routes")
public interface EmergencyServiceApi {

  @Operation(summary = "Create new emergency services", description = "create new emergency services with their details")
  public ResponseEntity<ApiResponse<List<EmergencyServiceResponseDTO>>> createEmergencyService(
      @Valid List<@Valid CreateEmergencyServiceRequest> requests, HttpServletRequest request);

  @Operation(summary = "Get emergency services within a coordinate", description = "Fetch emergency services with a coordinate within a radius")
  public ResponseEntity<ApiResponse<List<EmergencyServiceResponseDTO>>> findNearbyEmergencyServices(
      @DecimalMin(value = "-180.0", message = "longitude must be greater than or equal to -180.0") @DecimalMax(value = "180.0", message = "longitude must be less than or equal to 180.0") double longitude,
      @DecimalMin(value = "-90.0", message = "latitude must be greater than or equal -90.0") @DecimalMax(value = "90.0", message = "latitude must be less than or equal to 90.0") double latitude,
      @Parameter(description = "Search radius in meters", required = true) @Positive(message = "radius must be positive") double radius,

      @Min(value = 1) @Parameter(description = "Optional resource page number", required = false) int page,
      @Min(value = 1) @Max(value = 10) @Parameter(description = "Optional resource limit number", required = false) int limit,
      HttpServletRequest request) throws ResourceNotFoundException;
}
