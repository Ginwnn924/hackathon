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
        String resourceName = "vietnam.gol";
        File tempFile = null;
        
        try {
            // Tạo file tạm thời và đảm bảo nó được dọn dẹp
            tempFile = File.createTempFile("vietnam-gol-", ".tmp");
            tempFile.deleteOnExit(); 

            // Lấy InputStream từ JAR
            ClassPathResource resource = new ClassPathResource(resourceName);
            if (!resource.exists()) {
                 // Nếu không tìm thấy trong JAR (sau khi tải trong Dockerfile)
                 throw new IOException("Resource not found: " + resourceName + " in JAR.");
            }

            try (InputStream inputStream = resource.getInputStream()) {
                
                // Sao chép nội dung 384MB từ JAR sang file vật lý tạm thời
                long bytesCopied = Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                // KIỂM TRA ĐỘ TOÀN VẸN: Nếu file tạm thời rỗng, có thể quá trình copy lỗi
                if (bytesCopied == 0) {
                     throw new IOException("File copy failed, 0 bytes copied from resource: " + resourceName);
                }
                
                System.out.println("DEBUG: File " + resourceName + " extracted to: " + tempFile.getAbsolutePath() + " (" + bytesCopied + " bytes)");
            }

            // Mở FeatureLibrary bằng đường dẫn file vật lý vừa được giải nén
            return new FeatureLibrary(tempFile.getAbsolutePath());
            
        } catch (Exception e) {
            // Ném lại lỗi với thông tin chi tiết hơn
            throw new Exception("Lỗi khi tạo FeatureLibrary từ vietnam.gol: " + e.getMessage() + ". Đường dẫn tạm thời: " + (tempFile != null ? tempFile.getAbsolutePath() : "N/A"), e);
        }
    }
}