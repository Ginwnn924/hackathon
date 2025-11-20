package com.example.hackathon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.dto.UserTripRouteRequest;
import com.example.hackathon.dto.UserTripRouteResponse;
import com.example.hackathon.entity.UserTrip;
import com.example.hackathon.entity.UserTripRoute;
import com.example.hackathon.mapper.UserTripRouteMapper;
import com.example.hackathon.service.UserTripRouteService;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/routes")
@RestController
public class UserTripRouteController {
    @Autowired
    private UserTripRouteService service;

    @Autowired
    private UserTripRouteMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserTripRouteResponse>> getAllRoutes() {
        var routes = service.getAllRoutes();
        var response = routes.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTripRouteResponse> getRouteById(@PathVariable Long id) {
        var route = service.getRouteById(id);
        var response = mapper.toDTO(route);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<UserTripRouteResponse>> getRoutesByTripId(@PathVariable Long tripId) {
        var routes = service.getRoutesByTripId(tripId);
        var response = routes.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }
}
