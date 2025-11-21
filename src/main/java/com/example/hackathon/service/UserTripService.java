package com.example.hackathon.service;

import com.example.hackathon.dto.TravelRequest;
import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.entity.UserTrip;
import com.example.hackathon.entity.UserTripWaypoint;
import com.example.hackathon.mapper.UserTripMapper;
import com.example.hackathon.mapper.UserTripWaypointMapper;
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
    private UserTripWaypointService waypointService;

    @Autowired
    private UserTripMapper mapper;


    @Autowired
    private UserTripWaypointMapper waypointMapper;

    public List<UserTrip> getAllTrips() {
        return userTripRepository.findAll();
    }

    public UserTrip getTripById(Long id) {
        return userTripRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserTrip> getTripsByUserId(Long userId) {
        return userTripRepository.findByUserId(userId);
    }

    public UserTrip createTrip(TravelRequest travel) {
        UserTripRequest tripReq = travel.getTrip();
        UserTrip entity = new UserTrip();
        mapper.update(tripReq, entity);


        List<UserTripWaypoint> waypoints = travel.getWaypoints().stream()
                .map(waypointReq -> {
                    UserTripWaypoint waypoint = new UserTripWaypoint();
                    waypointMapper.update(waypointReq, waypoint);
                    waypoint.setCreatedAt(LocalDateTime.now());
                    waypoint.setUpdatedAt(LocalDateTime.now());

                    waypoint.setUserTrip(entity);
                    return waypoint;
                })
                .toList();

        // TODO: User missing CRUD so can't check
        UserEntity user = new UserEntity();
        user.setId(tripReq.getUserId());
        entity.setUser(user);

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