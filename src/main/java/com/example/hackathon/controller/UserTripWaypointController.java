package com.example.hackathon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.dto.UserTripWaypointResponse;
import com.example.hackathon.mapper.UserTripWaypointMapper;
import com.example.hackathon.service.UserTripWaypointService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/waypoints")
@RestController
@RequiredArgsConstructor
public class UserTripWaypointController {
    private final UserTripWaypointService service;
    private final UserTripWaypointMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserTripWaypointResponse>> getAllWaypoints() {
        var waypoints = service.getAllWaypoints();
        var response = waypoints.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTripWaypointResponse> getWaypointById(@PathVariable Long id) {
        var waypoint = service.getWaypointById(id);
        var response = mapper.toDTO(waypoint);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<UserTripWaypointResponse>> getWaypointsByTripId(@PathVariable Long tripId) {
        var waypoints = service.getWaypointsByTripId(tripId);
        var response = waypoints.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }
}
