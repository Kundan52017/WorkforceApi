package com.workforcemgmt.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for adding comments to tasks
 */
public class AddCommentRequest {
    @NotBlank(message = "Comment content is required")
    private String content;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "User name is required")
    private String userName;

    public AddCommentRequest() {}

    public AddCommentRequest(String content, String userId, String userName) {
        this.content = content;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
