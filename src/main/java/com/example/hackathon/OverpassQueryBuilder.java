package com.example.hackathon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverpassQueryBuilder {
    private String lat;
    private String lon;
    private int radius;
    private Map<String, List<String>> tags = new HashMap<>();
    private List<String> elementTypes = new ArrayList<>();

    public OverpassQueryBuilder around(int radius, String lat, String lon) {
        this.radius = radius;
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    public OverpassQueryBuilder addElementType(String type) {
        elementTypes.add(type.toLowerCase()); // node, way, relation
        return this;
    }

    public OverpassQueryBuilder addTag(String key, List<String> values) {
        tags.put(key, values);
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("[out:json][timeout:25];\n");
        sb.append("(\n");

        for (String type : elementTypes) {
            for (Map.Entry<String, List<String>> entry : tags.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();

                if (values == null || values.isEmpty()) {
                    sb.append(String.format("  %s[\"%s\"](around:%d,%s,%s);\n",
                            type, key, radius, lat, lon));
                } else {
                    for (String val : values) {
                        sb.append(String.format("  %s[\"%s\"=\"%s\"](around:%d,%s,%s);\n",
                                type, key, val, radius, lat, lon));
                    }
                }
            }
        }

        sb.append(");\n");
        sb.append("out center tags;\n");
        return sb.toString();
    }
}