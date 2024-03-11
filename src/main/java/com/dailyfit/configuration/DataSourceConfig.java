package com.dailyfit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Configuration
public class DataSourceConfig {
    @Bean
    public Connection dataBase() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
    }
}
