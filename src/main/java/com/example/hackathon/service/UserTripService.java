package com.example.hackathon.service;

import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.entity.UserTrip;
import com.example.hackathon.mapper.UserTripMapper;
import com.example.hackathon.repository.UserTripRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTripService {
    @Autowired
    private UserTripRepository userTripRepository;

    @Autowired
    private UserTripRouteService routeService;

    @Autowired
    private UserTripWaypointService waypointService;

    @Autowired
    private UserTripMapper mapper;

    public List<UserTrip> getAllTrips() {
        return userTripRepository.findAll();
    }

    public UserTrip getTripById(Long id) {
        return userTripRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserTrip> getTripsByUserId(Long userId) {
        return userTripRepository.findByUserId(userId);
    }

    public UserTrip createTrip(UserTripRequest trip) {
        UserTrip entity = new UserTrip();
        mapper.update(trip, entity);

        // TODO: User missing CRUD so can't check
        UserEntity user = new UserEntity();
        user.setId(trip.getUserId());
        entity.setUser(user);

        var routes = routeService.getAllRoutesByIds(trip.getRoutes());
        var waypoints = waypointService.getAllWaypointsByIds(trip.getWaypoints());
        
        for(var route : routes) {
            route.setUserTrip(entity);
        }
        
        for(var waypoint : waypoints) {
            waypoint.setUserTrip(entity);
        }

        entity.setRoutes(routes);
        entity.setWaypoints(waypoints);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return userTripRepository.save(entity);
    }

    public UserTrip updateTrip(Long id, UserTripRequest tripData) {
        var existing = userTripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));
        mapper.update(tripData, existing);
        existing.setUpdatedAt(LocalDateTime.now());

        return userTripRepository.save(existing);
    }

    public void deleteTrip(Long id) {
        if (!userTripRepository.existsById(id)) {
            throw new RuntimeException("Trip not found");
        }
        userTripRepository.deleteById(id);
    }
}