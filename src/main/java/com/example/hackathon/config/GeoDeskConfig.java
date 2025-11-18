package com.example.hackathon.config;

import com.geodesk.feature.FeatureLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoDeskConfig {
    @Bean
    public FeatureLibrary vietnamLibrary() {
        return new FeatureLibrary("vietnam.gol");
    }
}
