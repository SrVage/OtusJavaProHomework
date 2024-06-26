package com.example.Homework_14.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db/migration/schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("db/migration/data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }
}
