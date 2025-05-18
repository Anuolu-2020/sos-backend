package com.example.sosbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.builder.DSL.point;

import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sosbackend.dto.CreateIncidentReportRequest;
import com.example.sosbackend.dto.DeleteIncidentReportRequest;
import com.example.sosbackend.exceptions.ResourceNotFoundException;
import com.example.sosbackend.model.IncidentReportsModel;
import com.example.sosbackend.model.IncidentReportsPicturesModel;
import com.example.sosbackend.model.IncidentReportsVideosModel;
import com.example.sosbackend.repository.IncidentReportRepository;
import com.example.sosbackend.storage.StorageService;

import jakarta.transaction.Transactional;

@Service
public class IncidentReportService {

  private IncidentReportRepository incidentReportRepository;

  private StorageService storageService;

  public IncidentReportService(IncidentReportRepository incidentReportRepository, StorageService storageService) {
    this.incidentReportRepository = incidentReportRepository;
    this.storageService = storageService;
  }

  @Transactional
  public IncidentReportsModel createIncidentReport(CreateIncidentReportRequest request) {

    IncidentReportsModel incidentReport = new IncidentReportsModel();

    List<IncidentReportsPicturesModel> incidentReportsPictures = new ArrayList<>();
    List<IncidentReportsVideosModel> incidentReportsVideos = new ArrayList<>();

    // Collect all pictures into a list - pictures are required so this will never
    // be null
    List<MultipartFile> pictures = request.getPictures();

    // Collect all videos into a list - videos are optional
    List<MultipartFile> videos = request.getVideos();

    // Set incident report type of incident field
    incidentReport.setTypeOfIncident(request.getTypeOfIncident());

    // Set incident report description field
    incidentReport.setDescription(request.getDescription());

    incidentReport.setIsAddressed(false);

    // make the coordinate with the right coordinate reference system
    Point<G2D> coordinates = point(CoordinateReferenceSystems.WGS84,
        g(request.getLongitude(), request.getLatitude()));

    incidentReport.setCoordinates(coordinates);

    // Upload incident report pictures
    for (MultipartFile picture : pictures) {
      IncidentReportsPicturesModel incidentReportsPicture = new IncidentReportsPicturesModel();

      Map uploadResponse = this.storageService.uploadFile(picture, this.storageService.PICTURE_FOLDER_NAME);

      // set uploaded picture url
      incidentReportsPicture.setUrl((String) uploadResponse.get("secure_url"));

      // set uploaded picture key
      incidentReportsPicture.setKey((String) uploadResponse.get("public_id"));

      // Link child to parent table
      incidentReportsPicture.setIncidentReport(incidentReport);

      // Add each uploaded picture to the pictures array list
      incidentReportsPictures.add(incidentReportsPicture);
    }

    // Upload incident report videos if videos are provided
    if (videos != null && !videos.isEmpty()) {
      for (MultipartFile video : videos) {
        IncidentReportsVideosModel incidentReportsVideo = new IncidentReportsVideosModel();

        Map uploadResponse = this.storageService.uploadFile(video, this.storageService.VIDEO_FOLDER_NAME);

        // set uploaded video url
        incidentReportsVideo.setUrl((String) uploadResponse.get("secure_url"));

        // set uploaded video key
        incidentReportsVideo.setKey((String) uploadResponse.get("public_id"));

        // Link child to parent table
        incidentReportsVideo.setIncidentReport(incidentReport);

        // Add each uploaded video to the videos array list
        incidentReportsVideos.add(incidentReportsVideo);
      }
    }

    // set all the pictures records for saving
    incidentReport.setPictures(incidentReportsPictures);

    // set all the videos records for saving
    incidentReport.setVideos(incidentReportsVideos);

    // save the incident report along witn it's pictures and video
    return this.incidentReportRepository.save(incidentReport);
  }

  public List<IncidentReportsModel> findNearbyIncidents(double longitude, double latitude, double radius, int page,
      int limit) {

    int offset = ((page - 1) * limit);

    return incidentReportRepository.findNearbyIncidents(longitude,
        latitude, radius, limit, offset);
  }

  public void deleteIncidentReports(DeleteIncidentReportRequest deleteIncidentReportRequest) throws Exception {
    List<IncidentReportsModel> incidentReports = this.incidentReportRepository
        .findAllById(deleteIncidentReportRequest.getIncidentReportIds());

    List<String> pictureKeys = incidentReports.stream()
        .flatMap(r -> r.getPictures().stream())
        .map(IncidentReportsPicturesModel::getKey)
        .toList();

    // Handle videos - getVideos() might return an empty list if no videos were
    // uploaded
    List<String> videoKeys = incidentReports.stream()
        .flatMap(r -> r.getVideos() != null ? r.getVideos().stream() : List.<IncidentReportsVideosModel>of().stream())
        .map(IncidentReportsVideosModel::getKey)
        .toList();

    if (!pictureKeys.isEmpty()) {
      this.storageService.deleteFiles(pictureKeys, "image");
    }

    if (!videoKeys.isEmpty()) {
      this.storageService.deleteFiles(videoKeys, "video");
    }

    this.incidentReportRepository.deleteAllById(deleteIncidentReportRequest.getIncidentReportIds());
  }

  /**
     * Marks an incident as addressed by setting isAddressed to true.
     * @throws ResourceNotFoundException if incident not found.
     */
    @Transactional
    public void markIncidentAsAddressed(Long id) throws ResourceNotFoundException {
        IncidentReportsModel incident = incidentReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
        incident.setIsAddressed(true);
        incidentReportRepository.save(incident);
    }

}
