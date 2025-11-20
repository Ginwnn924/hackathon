package com.example.hackathon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.dto.UserTripWaypointRequest;
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

    @PostMapping
    public ResponseEntity<UserTripWaypointResponse> createWaypoint(@RequestBody UserTripWaypointRequest request) {
        var created = service.createWaypoint(request);
        var response = mapper.toDTO(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTripWaypointResponse> updateWaypoint(@PathVariable Long id, @RequestBody UserTripWaypointRequest request) {
        var updated = service.updateWaypoint(id, request);
        var response = mapper.toDTO(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaypoint(@PathVariable Long id) {
        service.deleteWaypoint(id);
        return ResponseEntity.noContent().build();
    }
}
