package com.example.hackathon.mapper;

import com.example.hackathon.dto.PlaceResponse;
import com.geodesk.feature.Feature;
import com.geodesk.feature.Relation;
import com.geodesk.feature.Way;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PlaceMapper {

    public List<PlaceResponse> mapFeatureToPlace(List<Feature> list, Feature f1) {
        Geometry first = f1.toGeometry();
        List<PlaceResponse> placeResponses = new ArrayList<>();
        for (Feature f : list) {
            PlaceResponse placeResponse = new PlaceResponse();
            placeResponse.setId(f.id());
            placeResponse.setType(f.type().toString());
            placeResponse.setDistance(f.toGeometry().distance(first));
            placeResponse.setLon(f.lon());
            placeResponse.setLat(f.lat());
            placeResponse.setTags(parseTags(f.tags().toString()));
            placeResponses.add(placeResponse);
            if (f instanceof Way) {
                placeResponse.setLat(f.nodes().first().lat());
                placeResponse.setLon(f.nodes().first().lon());
            }
            else if (f instanceof Relation) {
                Relation relation = (Relation) f;
                for (Feature member : relation.members()) {
                    if (member instanceof Way) {
                        Way way = (Way) member;
                        placeResponse.setLat(way.nodes().first().lat());
                        placeResponse.setLon(way.nodes().first().lon());
                        break;
                    }
                }
            }
            else {
                placeResponse.setLon(f.lon());
                placeResponse.setLat(f.lat());
            }
        }
        return placeResponses;
    }



    public static Map<String, String> parseTags(String tagLine) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> addressFields = new HashMap<>();

        // Split theo dấu phẩy, vì OSM style key=value
        String[] parts = tagLine.split(",");

        for (String part : parts) {
            String[] kv = part.split("=", 2); // split thành key=value, chỉ split 1 lần
            if (kv.length < 2) continue;

            String key = kv[0].trim();
            String value = kv[1].trim();

            if (key.startsWith("addr:")) {
                // Lưu riêng theo key để ghép theo thứ tự sau
                addressFields.put(key, value);
            } else {
                map.put(key, value);
            }
        }

        // Ghép address theo thứ tự chuẩn
        String[] order = {"addr:housenumber", "addr:street", "addr:subdistrict",
                "addr:district", "addr:city"};
        StringBuilder address = new StringBuilder();
        for (String key : order) {
            String val = addressFields.get(key);
            if (val != null && !val.isEmpty()) {
                if (address.length() > 0) address.append(" ");
                address.append(val);
            }
        }

        if (address.length() > 0) {
            map.put("address", address.toString());
        }

        return map;
    }
}
