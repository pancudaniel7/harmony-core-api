package com.devxsquad.harmony.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.devxsquad.harmony.model.entity"})
public class DatabaseConfig {
}
