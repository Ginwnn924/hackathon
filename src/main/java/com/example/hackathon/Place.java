package com.example.hackathon;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    private String type;
    private long id;
    private double lat;
    private double lon;
    private double distance;
    private Map<String, String> tags;

}


