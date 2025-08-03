package com.workforcemgmt.controller;

import com.workforcemgmt.dto.*;
import com.workforcemgmt.mapper.TaskMapper;
import com.workforcemgmt.model.Priority;
import com.workforcemgmt.model.Task;
import com.workforcemgmt.model.TaskStatus;
import com.workforcemgmt.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "APIs for managing workforce tasks, assignments, and operations")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Staff member not found")
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task createdTask = taskService.createTask(request);
        TaskDto taskDto = taskMapper.taskToTaskDto(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<TaskDetailsDto> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskWithFullDetails(id);
        TaskDetailsDto taskDetailsDto = taskMapper.taskToTaskDetailsDto(task);
        return ResponseEntity.ok(taskDetailsDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        TaskDto taskDto = taskMapper.taskToTaskDto(updatedTask);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // Bug Fix 1: Task Reassignment
    @PostMapping("/assign-by-ref")
    @Operation(summary = "Reassign task by customer reference", 
               description = "BUG FIX 1: Reassigns task to new staff, cancels old task to avoid duplicates")
    @ApiResponse(responseCode = "200", description = "Task reassigned successfully")
    @ApiResponse(responseCode = "404", description = "Task not found or staff member not found")
    public ResponseEntity<TaskDto> reassignTaskByCustomerReference(
            @RequestParam String customerReference,
            @RequestParam String newStaffId,
            @RequestParam String updatedBy) {
        Task reassignedTask = taskService.reassignTaskByCustomerReference(customerReference, newStaffId, updatedBy);
        TaskDto taskDto = taskMapper.taskToTaskDto(reassignedTask);
        return ResponseEntity.ok(taskDto);
    }

    // Bug Fix 2: Date Range Filtering
    @GetMapping("/date-range")
    @Operation(summary = "Get tasks by date range", 
               description = "BUG FIX 2: Excludes cancelled tasks for clean view")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully (excluding cancelled)")
    public ResponseEntity<List<TaskDto>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Task> tasks = taskService.getTasksByDateRange(startDate, endDate);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    // Feature 1: Smart Daily View
    @GetMapping("/smart-daily")
    @Operation(summary = "Get smart daily task view", 
               description = "FEATURE 1: Shows tasks starting in range PLUS active tasks from before")
    @ApiResponse(responseCode = "200", description = "Smart daily tasks retrieved successfully")
    public ResponseEntity<List<TaskDto>> getSmartDailyTasks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Task> tasks = taskService.getSmartDailyTasks(startDate, endDate);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    // Feature 2: Priority Management
    @PutMapping("/{id}/priority")
    @Operation(summary = "Update task priority")
    @ApiResponse(responseCode = "200", description = "Priority updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<TaskDto> updateTaskPriority(@PathVariable String id, @Valid @RequestBody UpdatePriorityRequest request) {
        Task updatedTask = taskService.updateTaskPriority(id, request.getPriority(), request.getUpdatedBy());
        TaskDto taskDto = taskMapper.taskToTaskDto(updatedTask);
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get tasks by priority")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    public ResponseEntity<List<TaskDto>> getTasksByPriority(@PathVariable Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    // Feature 3: Comments & Activity History
    @PostMapping("/{id}/comments")
    @Operation(summary = "Add comment to task", 
               description = "FEATURE 3: Adds comment and creates activity log entry")
    @ApiResponse(responseCode = "200", description = "Comment added successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "400", description = "Invalid comment data")
    public ResponseEntity<TaskDetailsDto> addCommentToTask(@PathVariable String id, @Valid @RequestBody AddCommentRequest request) {
        Task updatedTask = taskService.addCommentToTask(id, request.getUserId(), request.getUserName(), request.getContent());
        TaskDetailsDto taskDetailsDto = taskMapper.taskToTaskDetailsDto(updatedTask);
        return ResponseEntity.ok(taskDetailsDto);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update task status")
    @ApiResponse(responseCode = "200", description = "Status updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable String id, @RequestParam TaskStatus status, @RequestParam String updatedBy) {
        Task updatedTask = taskService.updateTaskStatus(id, status, updatedBy);
        TaskDto taskDto = taskMapper.taskToTaskDto(updatedTask);
        return ResponseEntity.ok(taskDto);
    }
}
