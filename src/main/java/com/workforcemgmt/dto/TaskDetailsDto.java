package com.workforcemgmt.dto;

import com.workforcemgmt.model.ActivityEntry;
import com.workforcemgmt.model.Comment;
import com.workforcemgmt.model.Priority;
import com.workforcemgmt.model.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for detailed task information including activity history and comments
 */
public class TaskDetailsDto {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private String assignedStaffId;
    private String assignedStaffName;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String customerReference;
    private List<ActivityEntry> activityHistory;
    private List<Comment> comments;

    public TaskDetailsDto() {}

    public TaskDetailsDto(String id, String title, String description, TaskStatus status, Priority priority,
                         String assignedStaffId, String assignedStaffName, LocalDate startDate, LocalDate dueDate,
                         LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String customerReference,
                         List<ActivityEntry> activityHistory, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignedStaffId = assignedStaffId;
        this.assignedStaffName = assignedStaffName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.customerReference = customerReference;
        this.activityHistory = activityHistory;
        this.comments = comments;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getAssignedStaffId() { return assignedStaffId; }
    public void setAssignedStaffId(String assignedStaffId) { this.assignedStaffId = assignedStaffId; }

    public String getAssignedStaffName() { return assignedStaffName; }
    public void setAssignedStaffName(String assignedStaffName) { this.assignedStaffName = assignedStaffName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getCustomerReference() { return customerReference; }
    public void setCustomerReference(String customerReference) { this.customerReference = customerReference; }

    public List<ActivityEntry> getActivityHistory() { return activityHistory; }
    public void setActivityHistory(List<ActivityEntry> activityHistory) { this.activityHistory = activityHistory; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
