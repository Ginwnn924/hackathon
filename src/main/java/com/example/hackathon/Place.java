package com.example.hackathon;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // Thêm ignoreUnknown = true

public class Place {
    private String type;
    private long id;
    private String lat;
    private String lon;
    private Center center; // dùng cho way/relation
    private Map<String, String> tags;

    @Getter
    @Setter
    public class Center {
        private String lat;
        private String lon;
        // getter / setter
    }
}


