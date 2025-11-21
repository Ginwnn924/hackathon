package com.example.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTripResponse {
    private Long id;
    private String name;
    private Double totalDistance;
    private Double totalDuration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserTripWaypointResponse> waypoints;
}
