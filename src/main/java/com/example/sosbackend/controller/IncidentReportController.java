package com.example.sosbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sosbackend.controller.interfaces.IncidentReportApi;
import com.example.sosbackend.dto.CreateIncidentReportRequest;
import com.example.sosbackend.dto.DeleteIncidentReportRequest;
import com.example.sosbackend.dto.IncidentReportResponseDTO;
import com.example.sosbackend.exceptions.ResourceNotFoundException;
import com.example.sosbackend.model.IncidentReportsModel;
import com.example.sosbackend.response.ApiResponse;
import com.example.sosbackend.service.IncidentReportService;
import com.example.sosbackend.util.ResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api")
public class IncidentReportController implements IncidentReportApi {

  private final IncidentReportService incidentReportService;

  public IncidentReportController(IncidentReportService incidentReportService) {
    this.incidentReportService = incidentReportService;
  }

  @PostMapping(path = "/incidentReport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse<IncidentReportResponseDTO>> CreateIncidentReport(
      @Valid @ModelAttribute CreateIncidentReportRequest incidentReportRequest, HttpServletRequest request)
      throws ResourceNotFoundException {

    IncidentReportsModel incidentReport = this.incidentReportService.createIncidentReport(incidentReportRequest);

    return ResponseEntity.ok(
        ResponseUtil.response(IncidentReportResponseDTO.from(incidentReport),
            0, 0,
            "Incident report created successfully", request.getRequestURI()));

  }

  @GetMapping("/incidents")
  public ResponseEntity<ApiResponse<List<IncidentReportResponseDTO>>> findNearbyIncidents(
      @RequestParam(required = true) double longitude,

      @RequestParam(required = true) double latitude,

      @RequestParam(required = true) double radius,

      @RequestParam(required = false, defaultValue = "1") int page,

      @RequestParam(required = false, defaultValue = "5") int limit,

      HttpServletRequest request) throws ResourceNotFoundException {

    List<IncidentReportsModel> incidentReports = this.incidentReportService.findNearbyIncidents(longitude, latitude,
        radius, page, limit);

    if (incidentReports.size() == 0) {
      throw new ResourceNotFoundException("No nearby incidents found for this coordinate");
    }

    return ResponseEntity.ok(ResponseUtil.response(IncidentReportResponseDTO.from(incidentReports), page, limit,
        "Nearby incidents successfully fetched", request.getRequestURI()));
  }

  @DeleteMapping(path = "incidentReport", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> deleteIncidentReports(
      @RequestBody DeleteIncidentReportRequest deleteIncidentReportRequest)
      throws Exception {

    this.incidentReportService.deleteIncidentReports(deleteIncidentReportRequest);

    HashMap<String, Object> response = new HashMap<>();

    response.put("status", true);
    response.put("message", "incident reports deleted successfully");

    return ResponseEntity.ok(response);
  }
}
