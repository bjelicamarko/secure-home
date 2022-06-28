package com.asdf.adminback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
 
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("https://localhost:4000")
                .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH")
                .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true);
    }
}
