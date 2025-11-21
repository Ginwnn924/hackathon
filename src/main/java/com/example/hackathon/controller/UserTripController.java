package com.example.hackathon.controller;

import java.util.List;

import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathon.dto.TravelRequest;
import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.dto.UserTripResponse;
import com.example.hackathon.mapper.UserTripMapper;
import com.example.hackathon.service.UserTripService;

@RequiredArgsConstructor
@RequestMapping("/api/trips")
@RestController
public class UserTripController {
    private final UserTripService service;
    private final UserTripMapper mapper;

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
    public ResponseEntity<UserTripResponse> createTrip(@RequestBody TravelRequest request) {
        var created = service.createTrip(request);
        var response = mapper.toDTO(created);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<UserTripResponse> inviteUserToTrip(@PathVariable Long id) {
        UserTripResponse result = service.inviteTrip(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{code}/join")
    public ResponseEntity<UserTripResponse> joinTripByCode(@PathVariable String code, Authentication authentication) {
        UserEntity user = ((CustomUserDetail) authentication.getPrincipal()).getUserEntity();

        UserTripResponse result = service.joinTripByCode(code, user);
        return ResponseEntity.ok(result);
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
