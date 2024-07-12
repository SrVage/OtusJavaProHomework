package com.example.homework14.configurations;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void initialize() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db/migration/schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("db/migration/data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }
}
