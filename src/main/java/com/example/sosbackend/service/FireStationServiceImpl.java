package com.example.sosbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.sosbackend.model.EmergencyServicesModel;
import com.example.sosbackend.exceptions.ResourceNotFoundException;

/**
 * Implements fire station service to filter fire stations.
 */
@Service
public class FireStationServiceImpl implements FireStationService {

    private final EmergencyService emergencyService;

    /**
     * Initializes with EmergencyService.
     */
    public FireStationServiceImpl(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    /**
     * Fetches and filters nearby fire stations.
     * @throws ResourceNotFoundException if no stations found.
     */
    @Override
    public List<EmergencyServicesModel> findNearbyFireStations(double longitude, double latitude, double radius, int page,
            int limit) throws ResourceNotFoundException {
        List<EmergencyServicesModel> allServices = emergencyService.findNearbyEmergencyServices(longitude, latitude, radius, limit, page);
        return allServices.stream()
                .filter(service -> "fire station".equalsIgnoreCase(service.getType()))
                .collect(Collectors.toList());
    }
}