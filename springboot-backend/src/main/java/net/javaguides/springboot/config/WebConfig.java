package net.javaguides.springboot.config;  // Use your own base package here

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // This annotation indicates this is a configuration class
public class WebConfig implements WebMvcConfigurer {  // Implements WebMvcConfigurer to customize Spring MVC configuration

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply CORS settings to all endpoints under /api/v1/
                .allowedOrigins("http://localhost:3000")  // Allow requests from localhost:3000 (React app)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow credentials (cookies, authentication)
    }
}
