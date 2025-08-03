package com.workforcemgmt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Staff information
 */
@Schema(description = "Staff member data transfer object")
public class StaffDto {
    
    @Schema(description = "Unique identifier for the staff member", 
            example = "staff-1", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Full name of the staff member", 
            example = "John Doe", required = true)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Schema(description = "Email address of the staff member", 
            example = "john.doe@company.com", required = true)
    private String email;
    
    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
    @Schema(description = "Department where the staff member works", 
            example = "Engineering", required = true)
    private String department;

    public StaffDto() {}

    public StaffDto(String id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
