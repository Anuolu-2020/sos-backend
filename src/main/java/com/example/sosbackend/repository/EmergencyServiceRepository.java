package com.example.sosbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sosbackend.model.EmergencyServicesModel;

public interface EmergencyServiceRepository extends JpaRepository<EmergencyServicesModel, Long> {

  @Query(value = "SELECT * FROM emergency_services WHERE ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) ORDER BY coordinates <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<EmergencyServicesModel> findNearbyEmergencyServices(
      @Param("longitude") double longitude,
      @Param("latitude") double latitude,
      @Param("radius") double radius,
      @Param("limit") int limit,
      @Param("offset") int offset);

  @Query(value = "SELECT * FROM emergency_services WHERE LOWER(type) = 'police station' AND ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) ORDER BY coordinates <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<EmergencyServicesModel> findNearbyPoliceStations(
      @Param("longitude") double longitude,
      @Param("latitude") double latitude,
      @Param("radius") double radius,
      @Param("limit") int limit,
      @Param("offset") int offset);

  @Query(value = "SELECT * FROM emergency_services WHERE LOWER(type) = 'hospital' AND ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) ORDER BY coordinates <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<EmergencyServicesModel> findNearbyHospitals(
      @Param("longitude") double longitude,
      @Param("latitude") double latitude,
      @Param("radius") double radius,
      @Param("limit") int limit,
      @Param("offset") int offset);

  @Query(value = "SELECT * FROM emergency_services WHERE LOWER(type) = 'fire station' AND ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) ORDER BY coordinates <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<EmergencyServicesModel> findNearbyFireStations(
      @Param("longitude") double longitude,
      @Param("latitude") double latitude,
      @Param("radius") double radius,
      @Param("limit") int limit,
      @Param("offset") int offset);
}
