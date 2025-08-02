package com.workforcemgmt.model;

import java.time.LocalDateTime;

/**
 * Model representing an activity entry in task history
 */
public class ActivityEntry {
    private String id;
    private String taskId;
    private String userId;
    private String userName;
    private String action;
    private String description;
    private LocalDateTime timestamp;

    public ActivityEntry() {}

    public ActivityEntry(String id, String taskId, String userId, String userName, String action, String description, LocalDateTime timestamp) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.userName = userName;
        this.action = action;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
