package com.example.hackathon.mapper;

import com.example.hackathon.Place;
import com.geodesk.feature.Feature;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PlaceMapper {

    public List<Place> mapFeatureToPlace(List<Feature> list, Feature f1) {
        Geometry first = f1.toGeometry();
        List<Place> places = new ArrayList<>();
        for (Feature f : list) {
            Place place = new Place();
            place.setId(f.id());
            place.setType(f.type().toString());
            place.setLat(f.lat());
            place.setDistance(f.toGeometry().distance(first));
            place.setLon(f.lon());
            place.setTags(parseTags(f.tags().toString()));
            places.add(place);
        }
        return places;
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
