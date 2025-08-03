package com.workforcemgmt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "System", description = "System information and health check endpoints")
public class WelcomeController {

    @GetMapping("/")
    @Operation(summary = "API Welcome")
    @ApiResponse(responseCode = "200", description = "Welcome information retrieved successfully")
    public ResponseEntity<Map<String, Object>> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Workforce Management API");
        response.put("version", "1.0.0");
        response.put("status", "RUNNING");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("tasks", "/api/tasks");
        endpoints.put("staff", "/api/staff");
        endpoints.put("health", "/actuator/health");
        endpoints.put("swagger-ui", "/swagger-ui/index.html");
        endpoints.put("api-docs", "/v3/api-docs");
        
        response.put("endpoints", endpoints);
        response.put("documentation", "Access /swagger-ui/index.html for interactive API documentation");
        response.put("postman", "Import the Postman collection for testing: Workforce-Management-API.postman_collection.json");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    @Operation(summary = "Basic health check")
    @ApiResponse(responseCode = "200", description = "Health status retrieved successfully")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Workforce Management API");
        return ResponseEntity.ok(response);
    }
}