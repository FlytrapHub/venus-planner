package com.flytrap.venusplanner.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://www.planner.flytraphub.net/")
                .allowedMethods(
                        HttpMethod.OPTIONS.name(), HttpMethod.GET.name(),
                        HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(), HttpMethod.DELETE.name(),
                        HttpMethod.HEAD.name())
                .allowCredentials(true)
                .maxAge(3600);
    }
}
