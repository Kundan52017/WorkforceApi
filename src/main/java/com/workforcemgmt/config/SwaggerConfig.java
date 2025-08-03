package com.workforcemgmt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI configuration for Workforce Management API
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workforce Management API")
                        .description("A comprehensive API for managing workforce tasks, staff assignments, and logistics operations. " +
                                   "This API provides endpoints for task management, staff operations, priority handling, " +
                                   "comments, and activity tracking.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev@workforcemgmt.com")
                                .url("https://github.com/workforcemgmt/api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.workforcemgmt.com")
                                .description("Production Server")
                ));
    }
}
