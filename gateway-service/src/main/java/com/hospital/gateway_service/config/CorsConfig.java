package com.hospital.gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
public class CorsConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {

    CorsConfiguration corsConfig = new CorsConfiguration();

    corsConfig.setAllowedOriginPatterns(List.of(
        "http://localhost:3000",
        "http://127.0.0.1:3000",
        "http://localhost:5173",
        "http://127.0.0.1:5173"
    ));
    
    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    corsConfig.setAllowedHeaders(List.of("*"));
    corsConfig.setAllowCredentials(true);
    corsConfig.setExposedHeaders(List.of("X-User-DNI", "X-User-Authorities"));
    corsConfig.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}