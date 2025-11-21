package com.example.hackathon.service;

import com.example.hackathon.dto.TravelRequest;
import com.example.hackathon.dto.UserTripRequest;
import com.example.hackathon.dto.UserTripResponse;
import com.example.hackathon.entity.ShareEntity;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.entity.UserTrip;
import com.example.hackathon.entity.UserTripWaypoint;
import com.example.hackathon.mapper.UserTripMapper;
import com.example.hackathon.mapper.UserTripWaypointMapper;
import com.example.hackathon.repository.ShareRepository;
import com.example.hackathon.repository.UserRepository;
import com.example.hackathon.repository.UserTripRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserTripService {
    @Autowired
    private UserTripRepository userTripRepository;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private UserRepository userRepository;

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

    public UserTripResponse inviteTrip(Long tripId) {
        UserTrip userTrip = userTripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userTrip.getCode() != null) {
            return mapper.toDTO(userTrip);
        }
        String token = generateToken();
        userTrip.setCode(token);
        userTripRepository.save(userTrip);
        return mapper.toDTO(userTrip);
    }

    private String generateToken() {
        int length = 15;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            token.append(characters.charAt(index));
        }
        return token.toString();
    }

    public List<UserTripResponse> getTripsByUserId(Long userId) {
        List<UserTripResponse> userTrips = new ArrayList<>();
        List<ShareEntity> listShare = shareRepository.findByUserId(userId);
        for (ShareEntity share : listShare) {
            UserTripResponse tripResponse = mapper.toDTO(share.getTrip());
            tripResponse.setOwner(share.getIsOwner());

            userTrips.add(tripResponse);
        }
        return userTrips;
    }

    @Transactional
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

        entity.setWaypoints(waypoints);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Lưu trip trước
        UserTrip savedTrip = userTripRepository.save(entity);

        // Lấy user từ database
        UserEntity user = userRepository.findById(tripReq.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + tripReq.getUserId()));

        // Tạo ShareEntity cho user là owner
        ShareEntity shareEntity = new ShareEntity();
        shareEntity.setIsOwner(true);
        shareEntity.setTrip(savedTrip);
        shareEntity.setUser(user);

        shareRepository.save(shareEntity);

        return savedTrip;
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

    public UserTripResponse joinTripByCode(String code, UserEntity user) {
        UserTrip trip = userTripRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Trip not found with code: " + code));


        ShareEntity shareEntity = new ShareEntity();
        shareEntity.setIsOwner(false);
        shareEntity.setTrip(trip);
        shareEntity.setUser(user);

        shareRepository.save(shareEntity);

        return mapper.toDTO(trip);
    }
}