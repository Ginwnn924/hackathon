package com.example.hackathon.config;

import com.geodesk.feature.FeatureLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;

@Configuration
@Slf4j
public class GeoDeskConfig {
    
    @Bean
    public FeatureLibrary vietnamLibrary() {
        // Các vị trí có thể tìm file
        String[] possiblePaths = {
            "/app/vietnam.gol",           // Docker path
            "vietnam.gol",                // Local path (current directory)
            System.getProperty("user.dir") + "/vietnam.gol"
        };
        
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                log.info("✓ Found vietnam.gol at: {}", path);
                log.info("✓ File size: {} MB", file.length() / (1024 * 1024));
                return new FeatureLibrary(path);
            }
        }
        
        // Nếu không tìm thấy, báo lỗi chi tiết
        log.error("✗ vietnam.gol not found!");
        log.error("Checked paths:");
        for (String path : possiblePaths) {
            log.error("  - {}", path);
        }
        throw new RuntimeException("vietnam.gol not found. Please ensure the file exists in project root or /app directory");
    }
}