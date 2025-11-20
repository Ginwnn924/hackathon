package com.example.hackathon.service;

import com.example.hackathon.dto.UserTripWaypointRequest;
import com.example.hackathon.entity.UserTripWaypoint;
import com.example.hackathon.mapper.UserTripWaypointMapper;
import com.example.hackathon.repository.UserTripWaypointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTripWaypointService {

    @Autowired
    private UserTripWaypointRepository waypointRepository;

    @Autowired
    private UserTripService tripService;

    @Autowired
    private UserTripWaypointMapper mapper;

    public List<UserTripWaypoint> getAllWaypoints() {
        return waypointRepository.findAll();
    }

    public UserTripWaypoint getWaypointById(Long id) {
        return waypointRepository.findById(id).orElseThrow(() -> new RuntimeException("Waypoint not found"));
    }

    public List<UserTripWaypoint> getWaypointsByTripId(Long tripId) {
        return waypointRepository.findByUserTripId(tripId);
    }

    public UserTripWaypoint createWaypoint(UserTripWaypointRequest request) {
        UserTripWaypoint entity = new UserTripWaypoint();
        mapper.update(request, entity);

        var trip = tripService.getTripById(request.getTripId());
        entity.setUserTrip(trip);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return waypointRepository.save(entity);
    }

    public UserTripWaypoint updateWaypoint(Long id, UserTripWaypointRequest request) {
        var existing = waypointRepository.findById(id).orElseThrow(() -> new RuntimeException("Waypoint not found"));
        mapper.update(request, existing);

        var trip = tripService.getTripById(request.getTripId());
        existing.setUserTrip(trip);
        existing.setUpdatedAt(LocalDateTime.now());

        return waypointRepository.save(existing);
    }

    public void deleteWaypoint(Long id) {
        if (!waypointRepository.existsById(id)) {
            throw new RuntimeException("Waypoint not found");
        }
        waypointRepository.deleteById(id);
    }
}