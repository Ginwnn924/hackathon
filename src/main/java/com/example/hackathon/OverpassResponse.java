package com.example.hackathon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // Thêm ignoreUnknown = true

public class OverpassResponse {

    private List<Place> elements;
}
