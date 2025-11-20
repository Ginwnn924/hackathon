package com.example.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTripRouteRequest {
    private String geometry;
    private Double distance;
    private Double duration;
}
