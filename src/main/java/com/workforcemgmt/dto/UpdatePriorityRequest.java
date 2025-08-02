package com.workforcemgmt.dto;

import com.workforcemgmt.model.Priority;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for updating task priority
 */
public class UpdatePriorityRequest {
    @NotNull(message = "Priority is required")
    private Priority priority;
    
    @NotNull(message = "Updated by is required")
    private String updatedBy;

    public UpdatePriorityRequest() {}

    public UpdatePriorityRequest(Priority priority, String updatedBy) {
        this.priority = priority;
        this.updatedBy = updatedBy;
    }

    // Getters and Setters
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}
