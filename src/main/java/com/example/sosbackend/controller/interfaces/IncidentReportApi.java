package com.example.sosbackend.controller.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.sosbackend.dto.CreateIncidentReportRequest;
import com.example.sosbackend.dto.DeleteIncidentReportRequest;
import com.example.sosbackend.dto.IncidentReportResponseDTO;
import com.example.sosbackend.exceptions.ResourceNotFoundException;
import com.example.sosbackend.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Tag(name = "Incident Report", description = "Apis for incident report routes")
public interface IncidentReportApi {

  @Operation(summary = "Create new incident report", description = "create new incident report with their details")
  public ResponseEntity<ApiResponse<IncidentReportResponseDTO>> CreateIncidentReport(
      @ModelAttribute @Valid CreateIncidentReportRequest incidentReportRequest, HttpServletRequest request)
      throws ResourceNotFoundException;

  @Operation(summary = "Get incidents within a coordinate", description = "Fetch incidents with a coordinate within a radius")
  public ResponseEntity<ApiResponse<List<IncidentReportResponseDTO>>> findNearbyIncidents(
      @DecimalMin(value = "-180.0", message = "longitude must be greater than or equal to -180.0") @DecimalMax(value = "180.0", message = "longitude must be less than or equal to 180.0") double longitude,
      @DecimalMin(value = "-90.0", message = "latitude must be greater than or equal -90.0") @DecimalMax(value = "90.0", message = "latitude must be less than or equal to 90.0") double latitude,
      @Parameter(description = "Search radius in meters", required = true) @Positive(message = "radius must be positive") double radius,

      @Min(value = 1) @Parameter(description = "Optional resource page number") int page,

      @Min(value = 1) @Max(value = 10) @Parameter(description = "Optional resource limit number") int limit,
      HttpServletRequest request) throws ResourceNotFoundException;

  @Operation(summary = "Delete incidents report", description = "Route for deleting incident repors")
  public ResponseEntity<Map<String, Object>> deleteIncidentReports(
      @RequestBody @Valid DeleteIncidentReportRequest deleteIncidentReportRequest)
      throws Exception;

  @Operation(summary = "Mark incident as addressed", description = "Updates the isAddressed status to true for a specific incident")
    ResponseEntity<ApiResponse<String>> markIncidentAsAddressed(
            @PathVariable @Min(1) @Parameter(description = "ID of the incident to address") Long id,
            HttpServletRequest request) throws ResourceNotFoundException;    
 
}
