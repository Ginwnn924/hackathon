package com.example.hackathon.service;

import com.example.hackathon.Place;
import com.example.hackathon.dto.PlaceRequest;
import com.example.hackathon.mapper.PlaceMapper;
import com.geodesk.feature.Feature;
import com.geodesk.feature.FeatureLibrary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final FeatureLibrary vietnamLibrary;
    private final PlaceMapper placeMapper;

    public List<Place> filterPlacesWithNameTag(PlaceRequest placeRequest) {
        Feature f1 = vietnamLibrary.select("na")  // n = node, a = area, w = way
                .containingLonLat(placeRequest.getLon(), placeRequest.getLat()).first();
        log.info("ID: {}, name: {}, Z: {}, Type: {}, Lat: {}, Lon: {}, Tags: {}",
                f1.id(),
                f1.stringValue("name"),
                f1.length(),
                f1.type(),
                f1.lat(),
                f1.lon(),
                f1.tags().toString()
        );
        List<String> listQueries = buildTagQueries(placeRequest.getTags(), "naw");
        List<Feature> results = new ArrayList<>();

        for (String query : listQueries) {
            results.addAll(
                    vietnamLibrary.select(query)
                            .maxMetersFromLonLat(placeRequest.getRadius(), placeRequest.getLon(), placeRequest.getLat())
                            .toList().stream().limit(placeRequest.getSize()).toList()
            );
        }
        return placeMapper.mapFeatureToPlace(results, f1);
    }


    // tao 1 ham builder tag to query
    public List<String> buildTagQueries(HashMap<String, List<String>> tags, String type) {
        List<String> queries = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : tags.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            // không value → selector dạng [key]
            if (values == null || values.isEmpty()) {
                queries.add(type + "[" + key + "][name]");
            }
            // có value → mỗi value tạo 1 query
            else {
                for (String value : values) {
                    queries.add(type + "[" + key + "=" + value + "][name]");
                }
            }
        }

        return queries;
    }

}
