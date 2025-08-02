package com.workforcemgmt.model;

import java.time.LocalDateTime;

/**
 * Model representing a user comment on a task
 */
public class Comment {
    private String id;
    private String taskId;
    private String userId;
    private String userName;
    private String content;
    private LocalDateTime timestamp;

    public Comment() {}

    public Comment(String id, String taskId, String userId, String userName, String content, LocalDateTime timestamp) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
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

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
