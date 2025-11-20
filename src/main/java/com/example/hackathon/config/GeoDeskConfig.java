package com.example.hackathon.config;

import com.geodesk.feature.FeatureLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Configuration
public class GeoDeskConfig {

    @Bean
    public FeatureLibrary vietnamLibrary() throws Exception {
        String filePath = "/app/vietnam.gol";
        File golFile = new File(filePath);

        if (!golFile.exists()) {
            throw new IOException("File không tồn tại: " + filePath);
        }

        long fileSize = golFile.length();
        System.out.println("DEBUG: Loading vietnam.gol from: " + filePath + " (" + fileSize + " bytes)");

        if (fileSize == 0) {
            throw new IOException("File rỗng: " + filePath);
        }

        return new FeatureLibrary(filePath);
    }
}