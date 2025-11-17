package com.example.hackathon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/overpass")
public class OverpassController {

    private final OverpassService overpassService;

    public OverpassController(OverpassService overpassService) {
        this.overpassService = overpassService;
    }

    @PostMapping("/search")
    public ResponseEntity<OverpassResponse> search(@RequestBody Request request) {
        OverpassResponse response = overpassService.queryOverpass(
                request.getLat(),
                request.getLon(),
                request.getRadius(),
                request.getTags()
        );
        // Filter các elements có tags là name
        return ResponseEntity.ok(response);
    }
}