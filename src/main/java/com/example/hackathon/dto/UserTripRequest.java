package com.example.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTripRequest {
    private String name;
    private Double totalDistance;
    private Double totalDuration;
    private Long userId;
}
