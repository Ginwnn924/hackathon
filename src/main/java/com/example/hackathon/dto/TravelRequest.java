package com.example.hackathon.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelRequest {
    private UserTripRequest trip;
    private List<UserTripRouteRequest> routes;
    private List<UserTripWaypointRequest> waypoints;   
}