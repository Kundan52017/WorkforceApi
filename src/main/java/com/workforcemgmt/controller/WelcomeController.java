package com.workforcemgmt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Welcome controller for the root endpoint
 */
@RestController
public class WelcomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Workforce Management API");
        response.put("version", "1.0.0");
        response.put("status", "RUNNING");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("tasks", "/api/tasks");
        endpoints.put("staff", "/api/staff");
        endpoints.put("health", "/actuator/health");
        
        response.put("endpoints", endpoints);
        response.put("documentation", "Access /api/tasks for task management and /api/staff for staff management");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Workforce Management API");
        return ResponseEntity.ok(response);
    }
}