package com.example.hackathon.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTripWaypointRequest {
    private Integer index;
    private String name;
    private Double lat;
    private Double lon;
    private LocalDateTime arriveAt;
    private LocalDateTime leaveAt;
    private String note;
}
