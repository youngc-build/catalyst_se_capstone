package com.example.defecttracker;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.defecttracker")
public class DefectTrackerConfig {
    // This configuration class is optional, but helps to make sure our JPA repositories are properly configured
}
