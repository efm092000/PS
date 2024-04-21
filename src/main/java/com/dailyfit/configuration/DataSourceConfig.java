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
        Connection connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite");
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        return connection;
    }
}
