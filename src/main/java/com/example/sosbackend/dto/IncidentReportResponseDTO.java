
package com.example.sosbackend.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.sosbackend.model.IncidentReportsModel;
import com.example.sosbackend.model.IncidentReportsPicturesModel;
import com.example.sosbackend.model.IncidentReportsVideosModel;

import lombok.Data;

@Data
public class IncidentReportResponseDTO {

  private Long id;

  private String typeOfIncident;

  private String description;

  private Boolean isAddressed;

  private List<IncidentReportsPicturesModel> pictures;

  private List<IncidentReportsVideosModel> videos;

  private double longitude;

  private double latitude;

  public static IncidentReportResponseDTO from(IncidentReportsModel model) {

    IncidentReportResponseDTO dto = new IncidentReportResponseDTO();

    dto.setId(model.getId());

    dto.setTypeOfIncident(model.getTypeOfIncident());

    dto.setDescription(model.getDescription());

    dto.setPictures(model.getPictures());

    dto.setVideos(model.getVideos());

    dto.setIsAddressed(model.getIsAddressed());

    if (model.getCoordinates() != null) {
      dto.setLongitude(model.getCoordinates().getPosition().getLon());
      dto.setLatitude(model.getCoordinates().getPosition().getLat());
    }
    return dto;
  }

  public static List<IncidentReportResponseDTO> from(List<IncidentReportsModel> models) {
    return models.stream()
        .map(IncidentReportResponseDTO::from)
        .collect(Collectors.toList());
  }

}
