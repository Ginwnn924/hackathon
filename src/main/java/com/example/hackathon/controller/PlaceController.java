package com.example.hackathon.controller;

import com.example.hackathon.dto.PlaceResponse;
import com.example.hackathon.dto.PlaceRequest;
import com.example.hackathon.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/overpass")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("/search")
    public ResponseEntity<List<PlaceResponse>> searchPlaces(@RequestBody PlaceRequest placeRequest) {

        return ResponseEntity.ok(placeService.filterPlacesWithNameTag(placeRequest));

    }








}