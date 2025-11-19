package com.example.hackathon.dto;


import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceResponse {
    private String type;
    private long id;
    private double lat;
    private double lon;
    private double distance;
    private Map<String, String> tags;

}


