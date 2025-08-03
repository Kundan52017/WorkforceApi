package com.workforcemgmt.controller;

import com.workforcemgmt.dto.*;
import com.workforcemgmt.mapper.TaskMapper;
import com.workforcemgmt.model.Priority;
import com.workforcemgmt.model.Task;
import com.workforcemgmt.model.TaskStatus;
import com.workforcemgmt.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for managing tasks
 */
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "APIs for managing workforce tasks, assignments, and operations")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new task", 
               description = "Creates a new task and assigns it to a staff member. Returns the created task details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public ResponseEntity<TaskDto> createTask(
            @Parameter(description = "Task creation request with all required details", required = true)
            @Valid @RequestBody CreateTaskRequest request) {
        Task createdTask = taskService.createTask(request);
        TaskDto taskDto = taskMapper.taskToTaskDto(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @GetMapping
    @Operation(summary = "Get all tasks", 
               description = "Retrieves a list of all tasks in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class)))
    })
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDetailsDto> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskWithFullDetails(id);
        TaskDetailsDto taskDetailsDto = taskMapper.taskToTaskDetailsDto(task);
        return ResponseEntity.ok(taskDetailsDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        TaskDto taskDto = taskMapper.taskToTaskDto(updatedTask);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reassign task by customer reference - fixes Bug 1
     */
    @PostMapping("/assign-by-ref")
    @Operation(summary = "Reassign task by customer reference", 
               description = "BUG FIX 1: Reassigns a task to a new staff member using customer reference. " +
                           "Properly cancels the old task and creates a new one to avoid duplicates.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task reassigned successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
        @ApiResponse(responseCode = "404", description = "Task not found or staff member not found")
    })
    public ResponseEntity<TaskDto> reassignTaskByCustomerReference(
            @Parameter(description = "Customer reference to identify the task", required = true)
            @RequestParam String customerReference,
            @Parameter(description = "ID of the new staff member to assign the task", required = true)
            @RequestParam String newStaffId,
            @Parameter(description = "ID of the user performing the reassignment", required = true)
            @RequestParam String updatedBy) {
        Task reassignedTask = taskService.reassignTaskByCustomerReference(customerReference, newStaffId, updatedBy);
        TaskDto taskDto = taskMapper.taskToTaskDto(reassignedTask);
        return ResponseEntity.ok(taskDto);
    }

    /**
     * Get tasks by date range - fixes Bug 2 (excludes cancelled tasks)
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<TaskDto>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Task> tasks = taskService.getTasksByDateRange(startDate, endDate);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    /**
     * Smart daily task view - Feature 1
     */
    @GetMapping("/smart-daily")
    @Operation(summary = "Get smart daily task view", 
               description = "FEATURE 1: Enhanced daily view that shows tasks starting in the date range " +
                           "PLUS active tasks that started before but are still open. Perfect for 'today's work' view.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Smart daily tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class)))
    })
    public ResponseEntity<List<TaskDto>> getSmartDailyTasks(
            @Parameter(description = "Start date for the range (ISO date format: YYYY-MM-DD)", required = true, example = "2025-08-03")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date for the range (ISO date format: YYYY-MM-DD)", required = true, example = "2025-08-03")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Task> tasks = taskService.getSmartDailyTasks(startDate, endDate);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    /**
     * Update task priority - Feature 2
     */
    @PutMapping("/{id}/priority")
    public ResponseEntity<TaskDto> updateTaskPriority(
            @PathVariable String id,
            @Valid @RequestBody UpdatePriorityRequest request) {
        Task updatedTask = taskService.updateTaskPriority(id, request.getPriority(), request.getUpdatedBy());
        TaskDto taskDto = taskMapper.taskToTaskDto(updatedTask);
        return ResponseEntity.ok(taskDto);
    }

    /**
     * Get tasks by priority - Feature 2
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDto>> getTasksByPriority(@PathVariable Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks);
        return ResponseEntity.ok(taskDtos);
    }

    /**
     * Add comment to task - Feature 3
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<TaskDetailsDto> addCommentToTask(
            @PathVariable String id,
            @Valid @RequestBody AddCommentRequest request) {
        Task updatedTask = taskService.addCommentToTask(id, request.getUserId(), request.getUserName(), request.getContent());
        TaskDetailsDto taskDetailsDto = taskMapper.taskToTaskDetailsDto(updatedTask);
        return ResponseEntity.ok(taskDetailsDto);
    }

    /**
     * Update task status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(
            @PathVariable String id,
            @RequestParam TaskStatus status,
            @RequestParam String updatedBy) {
        Task updatedTask = taskService.updateTaskStatus(id, status, updatedBy);
        TaskDto taskDto = taskMapper.taskToTaskDto(updatedTask);
        return ResponseEntity.ok(taskDto);
    }
}
