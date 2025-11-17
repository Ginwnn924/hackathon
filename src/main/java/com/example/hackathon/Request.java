package com.example.hackathon;

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
public class Request {
    private String lat;
    private String lon;
    private int radius;
    HashMap<String, List<String>> tags;
}
