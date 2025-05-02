package com.example.sosbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sosbackend.model.IncidentReportsModel;

public interface IncidentReportRepository extends JpaRepository<IncidentReportsModel, Long> {

  @Query(value = "SELECT * FROM incident_reports WHERE ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) ORDER BY coordinates <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<IncidentReportsModel> findNearbyIncidents(
      @Param("longitude") double longitude,
      @Param("latitude") double latitude,
      @Param("radius") double radius,
      @Param("limit") int limit,
      @Param("offset") int offset);

}
