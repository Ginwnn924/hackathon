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

import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.dto.UserTripResponse;
import com.example.hackathon.mapper.UserTripMapper;
import com.example.hackathon.service.UserTripService;

@RequestMapping("/api/trips")
@RestController
public class UserTripController {
    @Autowired
    private UserTripService service;
    @Autowired
    private UserTripMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserTripResponse>> getUserProfile() {
        var trips = service.getAllTrips();
        var response = trips.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTripResponse> getTripById(@PathVariable Long id) {
        var trip = service.getTripById(id);
        var response = mapper.toDTO(trip);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserTripResponse> createTrip(@RequestBody UserTripRequest request) {
        var created = service.createTrip(request);
        var response = mapper.toDTO(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTripResponse> updateTrip(@PathVariable Long id, @RequestBody UserTripRequest request) {
        var updated = service.updateTrip(id, request);
        var response = mapper.toDTO(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        service.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
