package com.example.sosbackend.service;

import java.util.List;

import com.example.sosbackend.model.EmergencyServicesModel;
import com.example.sosbackend.exceptions.ResourceNotFoundException;

/**
 * Defines fire station service operations.
 */
public interface FireStationService {
    /**
     * Retrieves nearby fire stations.
     * @throws ResourceNotFoundException if no stations found.
     */
    List<EmergencyServicesModel> findNearbyFireStations(double longitude, double latitude, double radius, int page, int limit)
            throws ResourceNotFoundException;
}
