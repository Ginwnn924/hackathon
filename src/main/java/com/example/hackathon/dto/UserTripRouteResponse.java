package com.example.hackathon.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTripRouteResponse {
    private Long id;
    private String geometry;
    private Double distance;
    private Double duration;
    private LocalDateTime createdAt;
}
