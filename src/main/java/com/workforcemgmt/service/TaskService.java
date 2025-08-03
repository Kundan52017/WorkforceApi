package com.workforcemgmt.service;

import com.workforcemgmt.dto.CreateTaskRequest;
import com.workforcemgmt.exception.ResourceNotFoundException;
import com.workforcemgmt.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing tasks, including CRUD operations and business logic
 */
@Service
public class TaskService {
    private final Map<String, Task> taskStorage = new ConcurrentHashMap<>();
    private final StaffService staffService;

    public TaskService(StaffService staffService) {
        this.staffService = staffService;
    }

    public Task createTask(CreateTaskRequest request) {
        // Validate staff exists
        Staff assignedStaff = staffService.getStaffById(request.getAssignedStaffId());
        
        String taskId = UUID.randomUUID().toString();
        Task task = new Task(
            taskId,
            request.getTitle(),
            request.getDescription(),
            TaskStatus.ACTIVE,
            request.getPriority(),
            request.getAssignedStaffId(),
            assignedStaff.getName(),
            request.getStartDate(),
            request.getDueDate(),
            request.getCreatedBy(),
            request.getCustomerReference()
        );

        taskStorage.put(taskId, task);
        
        // Log activity
        addActivityEntry(task, request.getCreatedBy(), request.getCreatedBy(), 
                        "CREATED", "Task created and assigned to " + assignedStaff.getName());
        
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskStorage.values());
    }

    public Task getTaskById(String id) {
        Task task = taskStorage.get(id);
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        return task;
    }

    public Task updateTask(String id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        
        updatedTask.setId(id);
        updatedTask.setCreatedAt(existingTask.getCreatedAt());
        updatedTask.setUpdatedAt(LocalDateTime.now());
        updatedTask.setActivityHistory(existingTask.getActivityHistory());
        updatedTask.setComments(existingTask.getComments());
        
        taskStorage.put(id, updatedTask);
        return updatedTask;
    }

    public void deleteTask(String id) {
        if (!taskStorage.containsKey(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskStorage.remove(id);
    }

    /**
     * Reassign task to a new staff member by customer reference
     * Fixes Bug 1: Properly cancels old task instead of creating duplicates
     */
    public Task reassignTaskByCustomerReference(String customerReference, String newStaffId, String updatedBy) {
        // Validate new staff exists
        Staff newStaff = staffService.getStaffById(newStaffId);
        
        // Find existing active task for this customer reference
        Task existingTask = taskStorage.values().stream()
            .filter(task -> customerReference.equals(task.getCustomerReference()) && 
                           task.getStatus() == TaskStatus.ACTIVE)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("No active task found for customer reference: " + customerReference));

        // Cancel the old task
        existingTask.setStatus(TaskStatus.CANCELLED);
        existingTask.setUpdatedAt(LocalDateTime.now());
        addActivityEntry(existingTask, updatedBy, updatedBy, 
                        "CANCELLED", "Task cancelled due to reassignment");

        // Create new task with the same details but assigned to new staff
        String newTaskId = UUID.randomUUID().toString();
        Task newTask = new Task(
            newTaskId,
            existingTask.getTitle(),
            existingTask.getDescription(),
            TaskStatus.ACTIVE,
            existingTask.getPriority(),
            newStaffId,
            newStaff.getName(),
            existingTask.getStartDate(),
            existingTask.getDueDate(),
            updatedBy,
            customerReference
        );

        taskStorage.put(newTaskId, newTask);
        
        // Log activity for new task
        addActivityEntry(newTask, updatedBy, updatedBy, 
                        "CREATED", "Task reassigned from " + existingTask.getAssignedStaffName() + " to " + newStaff.getName());
        
        return newTask;
    }

    /**
     * Get tasks by date range, excluding cancelled tasks
     * Fixes Bug 2: Filters out cancelled tasks from the result
     */
    public List<Task> getTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return taskStorage.values().stream()
            .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Exclude cancelled tasks
            .filter(task -> {
                LocalDate taskStartDate = task.getStartDate();
                return taskStartDate != null && 
                       !taskStartDate.isBefore(startDate) && 
                       !taskStartDate.isAfter(endDate);
            })
            .collect(Collectors.toList());
    }

    /**
     * Smart daily task view - Feature 1
     * Returns tasks that started in the date range PLUS active tasks that started before but are still open
     */
    public List<Task> getSmartDailyTasks(LocalDate startDate, LocalDate endDate) {
        return taskStorage.values().stream()
            .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Exclude cancelled tasks
            .filter(task -> {
                LocalDate taskStartDate = task.getStartDate();
                if (taskStartDate == null) return false;
                
                // Tasks that started within the range
                boolean startedInRange = !taskStartDate.isBefore(startDate) && !taskStartDate.isAfter(endDate);
                
                // Active tasks that started before the range but are still open
                boolean activeFromBefore = taskStartDate.isBefore(startDate) && 
                                         task.getStatus() == TaskStatus.ACTIVE;
                
                return startedInRange || activeFromBefore;
            })
            .collect(Collectors.toList());
    }

    /**
     * Update task priority - Feature 2
     */
    public Task updateTaskPriority(String taskId, Priority newPriority, String updatedBy) {
        Task task = getTaskById(taskId);
        Priority oldPriority = task.getPriority();
        
        task.setPriority(newPriority);
        task.setUpdatedAt(LocalDateTime.now());
        
        addActivityEntry(task, updatedBy, updatedBy, 
                        "PRIORITY_CHANGED", 
                        "Priority changed from " + oldPriority + " to " + newPriority);
        
        return task;
    }

    /**
     * Get tasks by priority - Feature 2
     */
    public List<Task> getTasksByPriority(Priority priority) {
        return taskStorage.values().stream()
            .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Exclude cancelled tasks
            .filter(task -> priority.equals(task.getPriority()))
            .collect(Collectors.toList());
    }

    /**
     * Add comment to task - Feature 3
     */
    public Task addCommentToTask(String taskId, String userId, String userName, String content) {
        Task task = getTaskById(taskId);
        
        Comment comment = new Comment(
            UUID.randomUUID().toString(),
            taskId,
            userId,
            userName,
            content,
            LocalDateTime.now()
        );
        
        task.getComments().add(comment);
        task.setUpdatedAt(LocalDateTime.now());
        
        addActivityEntry(task, userId, userName, "COMMENT_ADDED", "Comment added: " + content);
        
        return task;
    }

    /**
     * Update task status
     */
    public Task updateTaskStatus(String taskId, TaskStatus newStatus, String updatedBy) {
        Task task = getTaskById(taskId);
        TaskStatus oldStatus = task.getStatus();
        
        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());
        
        addActivityEntry(task, updatedBy, updatedBy, 
                        "STATUS_CHANGED", 
                        "Status changed from " + oldStatus + " to " + newStatus);
        
        return task;
    }

    /**
     * Helper method to add activity entries - Feature 3
     */
    private void addActivityEntry(Task task, String userId, String userName, String action, String description) {
        ActivityEntry entry = new ActivityEntry(
            UUID.randomUUID().toString(),
            task.getId(),
            userId,
            userName,
            action,
            description,
            LocalDateTime.now()
        );
        
        task.getActivityHistory().add(entry);
        
        // Sort activity history chronologically
        task.getActivityHistory().sort(Comparator.comparing(ActivityEntry::getTimestamp));
    }

    /**
     * Get task with full details including activity history and comments - Feature 3
     */
    public Task getTaskWithFullDetails(String taskId) {
        Task task = getTaskById(taskId);
        
        // Sort activity history and comments chronologically
        task.getActivityHistory().sort(Comparator.comparing(ActivityEntry::getTimestamp));
        task.getComments().sort(Comparator.comparing(Comment::getTimestamp));
        
        return task;
    }
}
