package com.example.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequest {
    private double lat;
    private double lon;
    private int size = 10;
    private int radius;
    HashMap<String, List<String>> tags;
}
