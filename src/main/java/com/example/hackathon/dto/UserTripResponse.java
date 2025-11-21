package com.example.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTripResponse {
    private Long id;
    private String name;
    private boolean isOwner;
    private String code;
    private Double totalDistance;
    private Double totalDuration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserTripWaypointResponse> waypoints;
}
