package com.example.hackathon.service;

import com.example.hackathon.entity.UserTripWaypoint;
import com.example.hackathon.mapper.UserTripWaypointMapper;
import com.example.hackathon.repository.UserTripWaypointRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTripWaypointService {

    private UserTripWaypointRepository waypointRepository;

    public List<UserTripWaypoint> getAllWaypoints() {
        return waypointRepository.findAll();
    }

    public UserTripWaypoint getWaypointById(Long id) {
        return waypointRepository.findById(id).orElseThrow(() -> new RuntimeException("Waypoint not found"));
    }

    public List<UserTripWaypoint> getWaypointsByTripId(Long tripId) {
        return waypointRepository.findByUserTripId(tripId);
    }

    public List<UserTripWaypoint> getAllWaypointsByIds(List<Long> ids) {
        return waypointRepository.findAllById(ids);
    }
}