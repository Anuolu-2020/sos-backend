package com.example.sosbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sosbackend.model.EmergencyServicesModel;

public interface EmergencyServiceRepository extends JpaRepository<EmergencyServicesModel, Long> {

  @Query(value = "SELECT * FROM emergency_services WHERE ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius)", nativeQuery = true)
  List<EmergencyServicesModel> findNearbyEmergencyServices(
      @Param("longitude") double longitude,
      @Param("latitude") double latitude,
      @Param("radius") double radius);
}
