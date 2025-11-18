package com.example.hackathon.controller;

import com.example.hackathon.OverpassResponse;
import com.example.hackathon.Place;
import com.example.hackathon.mapper.PlaceMapper;
import com.example.hackathon.service.OverpassService;
import com.example.hackathon.dto.PlaceRequest;
import com.example.hackathon.service.PlaceService;
import com.geodesk.feature.Feature;
import com.geodesk.feature.FeatureLibrary;
import com.geodesk.feature.Features;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/overpass")
@RequiredArgsConstructor
@Slf4j
public class OverpassController {
    private final PlaceService placeService;

    @PostMapping("/search")
    public ResponseEntity<List<Place>> searchPlaces(@RequestBody PlaceRequest placeRequest) {

        return ResponseEntity.ok(placeService.filterPlacesWithNameTag(placeRequest));

    }








}