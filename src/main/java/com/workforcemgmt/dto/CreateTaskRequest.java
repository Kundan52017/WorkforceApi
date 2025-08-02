package com.workforcemgmt.dto;

import com.workforcemgmt.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO for creating new tasks
 */
public class CreateTaskRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Priority is required")
    private Priority priority;
    
    @NotNull(message = "Assigned staff ID is required")
    private String assignedStaffId;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    @NotBlank(message = "Created by is required")
    private String createdBy;
    
    private String customerReference;

    public CreateTaskRequest() {}

    public CreateTaskRequest(String title, String description, Priority priority, String assignedStaffId, 
                           LocalDate startDate, LocalDate dueDate, String createdBy, String customerReference) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assignedStaffId = assignedStaffId;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.customerReference = customerReference;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getAssignedStaffId() { return assignedStaffId; }
    public void setAssignedStaffId(String assignedStaffId) { this.assignedStaffId = assignedStaffId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getCustomerReference() { return customerReference; }
    public void setCustomerReference(String customerReference) { this.customerReference = customerReference; }
}
