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

    /**
     * Khởi tạo FeatureLibrary cho vietnam.gol.
     * Phương thức này giải nén file vietnam.gol từ bên trong JAR ra một file tạm thời
     * trên hệ thống để thư viện GeoDesk có thể truy cập được.
     */
    @Bean
    public FeatureLibrary vietnamLibrary() throws Exception {

        String resourceName = "vietnam.gol";
        
        File tempFile = File.createTempFile("vietnam-gol-", ".tmp");
        tempFile.deleteOnExit(); 
        try (InputStream inputStream = new ClassPathResource(resourceName).getInputStream()) {
            
            if (inputStream == null) {
                throw new IOException("Không tìm thấy file " + resourceName + " trong JAR. Kiểm tra lại tên file và Dockerfile.");
            }
            
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("DEBUG: File 384MB đã được giải nén thành công vào: " + tempFile.getAbsolutePath());
        } catch (IOException e) {
             throw new IOException("Lỗi IO khi giải nén file " + resourceName + ": " + e.getMessage(), e);
        }

        return new FeatureLibrary(tempFile.getAbsolutePath());
    }
}