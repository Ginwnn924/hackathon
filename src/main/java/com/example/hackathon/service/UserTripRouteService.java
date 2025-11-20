package com.example.hackathon.service;

import com.example.hackathon.dto.UserTripRouteRequest;
import com.example.hackathon.entity.UserTripRoute;
import com.example.hackathon.mapper.UserTripRouteMapper;
import com.example.hackathon.repository.UserTripRouteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTripRouteService {

    @Autowired
    private UserTripRouteRepository routeRepository;

    @Autowired
    private UserTripService tripService;

    @Autowired
    private UserTripRouteMapper mapper;

    public List<UserTripRoute> getAllRoutes() {
        return routeRepository.findAll();
    }

    public UserTripRoute getRouteById(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public List<UserTripRoute> getRoutesByTripId(Long tripId) {
        return routeRepository.findByUserTripId(tripId);
    }

    public UserTripRoute createRoute(UserTripRouteRequest route) {
        UserTripRoute entity = new UserTripRoute();
        mapper.update(route, entity);

        var trip = tripService.getTripById(route.getTripId());
        entity.setUserTrip(trip);
        entity.setCreatedAt(LocalDateTime.now());

        return routeRepository.save(entity);
    }

    public UserTripRoute updateRoute(Long id, UserTripRouteRequest routeData) {
        var existing = routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Route not found"));
        mapper.update(routeData, existing);

        var trip = tripService.getTripById(routeData.getTripId());
        existing.setUserTrip(trip);

        return routeRepository.save(existing);
    }

    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Route not found");
        }
        routeRepository.deleteById(id);
    }
}