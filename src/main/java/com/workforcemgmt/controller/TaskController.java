package com.workforcemgmt.controller;

import com.workforcemgmt.dto.*;
import com.workforcemgmt.mapper.TaskMapper;
import com.workforcemgmt.model.Priority;
import com.workforcemgmt.model.Task;
import com.workforcemgmt.model.TaskStatus;
import com.workforcemgmt.service.TaskService;
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
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task createdTask = taskService.createTask(request);
        TaskDto taskDto = taskMapper.taskToTaskDto(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @GetMapping
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
    public ResponseEntity<TaskDto> reassignTaskByCustomerReference(
            @RequestParam String customerReference,
            @RequestParam String newStaffId,
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
    public ResponseEntity<List<TaskDto>> getSmartDailyTasks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
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
