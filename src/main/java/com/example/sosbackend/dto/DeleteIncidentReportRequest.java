package com.example.sosbackend.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeleteIncidentReportRequest {

  @NotNull(message = "incidentReportIds is required")
  @Size(min = 1)
  private List<@NotNull(message = "ID cannot be null") @Min(value = 1, message = "ID must be positive") Long> incidentReportIds;

}
