package com.example.hackathon.service;

import com.example.hackathon.entity.UserTripRoute;
import com.example.hackathon.mapper.UserTripRouteMapper;
import com.example.hackathon.repository.UserTripRouteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTripRouteService {

    @Autowired
    private UserTripRouteRepository routeRepository;

    public List<UserTripRoute> getAllRoutes() {
        return routeRepository.findAll();
    }

    public UserTripRoute getRouteById(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public List<UserTripRoute> getRoutesByTripId(Long tripId) {
        return routeRepository.findByUserTripId(tripId);
    }

    public List<UserTripRoute> getAllRoutesByIds(List<Long> ids) {
        return routeRepository.findAllById(ids);
    }
}