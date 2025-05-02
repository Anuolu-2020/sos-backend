package com.example.sosbackend.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.sosbackend.util.ValidMultipartFileList;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateIncidentReportRequest {

  @Pattern(regexp = "fire|health|security|others")
  @NotBlank(message = "typeOfIncident is required")
  private String typeOfIncident;

  @NotBlank(message = "description is required")
  private String description;

  @ValidMultipartFileList(allowed = { "image/png", "image/jpeg" }, message = "pictures must be PNG or JPEG images.")
  private List<MultipartFile> pictures;

  @ValidMultipartFileList(allowed = { "video/mp4", "video/webm" }, message = "videos must be MP4 or WEBM format.")
  private List<MultipartFile> videos;

  @NotNull(message = "Longitude is required")
  @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
  @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
  private Double longitude;

  @NotNull(message = "Latitude is required")
  @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
  @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
  private Double latitude;

}
