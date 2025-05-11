package com.example.sosbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sosbackend.controller.interfaces.EmergencyServiceApi;
import com.example.sosbackend.dto.CreateEmergencyServiceRequest;
import com.example.sosbackend.dto.EmergencyServiceResponseDTO;
import com.example.sosbackend.exceptions.ResourceNotFoundException;
import com.example.sosbackend.model.EmergencyServicesModel;
import com.example.sosbackend.response.ApiResponse;
import com.example.sosbackend.service.EmergencyService;
import com.example.sosbackend.util.ResponseUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Validated
public class EmergencyServiceController implements EmergencyServiceApi {
  private final EmergencyService emergencyService;

  public EmergencyServiceController(EmergencyService emergencyService) {
    this.emergencyService = emergencyService;
  }

  @PostMapping("/emergencyServices")
  public ResponseEntity<ApiResponse<List<EmergencyServiceResponseDTO>>> createEmergencyService(
      @RequestBody List<CreateEmergencyServiceRequest> requests, HttpServletRequest request) {
    List<EmergencyServicesModel> emergencyServices = this.emergencyService.createEmergencyServices(requests);

    return ResponseEntity.ok(
        ResponseUtil.response(EmergencyServiceResponseDTO.from(emergencyServices), 0, 0,
            "Emergency services created successfully", request.getRequestURI()));
  }

  @GetMapping("/emergencyServices")
  public ResponseEntity<ApiResponse<List<EmergencyServiceResponseDTO>>> findNearbyEmergencyServices(
      @RequestParam(required = true) double longitude,

      @RequestParam(required = true) double latitude,

      @RequestParam(required = true) double radius,

      @RequestParam(required = false, defaultValue = "1") int page,

      @RequestParam(required = false, defaultValue = "5") int limit,

      HttpServletRequest request) throws ResourceNotFoundException {

    List<EmergencyServicesModel> emergencyServices = this.emergencyService
        .findNearbyEmergencyServices(longitude, latitude, radius, limit, page);

    if (emergencyServices.size() == 0) {
      throw new ResourceNotFoundException("No nearby emergency services found for this coordinate");
    }

    return ResponseEntity.ok(ResponseUtil.response(EmergencyServiceResponseDTO.from(emergencyServices), page, limit,
        "Nearby Emergency services successfully fetched", request.getRequestURI()));
  }
/*
 * how do I implement fetching police stations and hospitals based on their
 * cooordinates. each with their respective routes
 */

}
