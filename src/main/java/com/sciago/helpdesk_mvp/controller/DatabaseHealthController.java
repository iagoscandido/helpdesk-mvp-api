package com.sciago.helpdesk_mvp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

@RestController
public class DatabaseHealthController {
    private final DataSource datasource;

    public DatabaseHealthController (DataSource datasource) {
        this.datasource = datasource;
    }

    @GetMapping("/health/db")
    public ResponseEntity<Map<String, String>> checkDatabase() {
        try (Connection connection = datasource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1"); {
                preparedStatement.execute();
            }
            return ResponseEntity.ok(Map.of("database", "UP"));
        } catch (Exception e) {
            return ResponseEntity.status(503).body(Map.of("database", "DOWN"));
        }
    }

}
