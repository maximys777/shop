package com.maximys777.shop.config;

import com.cloudinary.Cloudinary;
import com.maximys777.shop.config.properties.CloudinaryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
    private final CloudinaryProperties properties;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", properties.getCloudName(),
                "api_key", properties.getApiKey(),
                "api_secret", properties.getApiSecret()
        ));
    }
}
