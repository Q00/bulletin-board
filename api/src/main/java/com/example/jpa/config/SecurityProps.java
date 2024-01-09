package com.example.jpa.config;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("spring.security")
@Getter
@Data
public class SecurityProps {
    private final Cors cors;

    @RequiredArgsConstructor
    @Getter
    public static class Cors {
        private final List<String> allowedOrigins;
        private final List<String> allowedMethods;
        private final boolean allowCredentials;
        private final List<String> allowedHeaders;
    }
}
