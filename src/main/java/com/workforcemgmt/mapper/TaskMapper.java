package com.workforcemgmt.mapper;

import com.workforcemgmt.dto.TaskDto;
import com.workforcemgmt.dto.TaskDetailsDto;
import com.workforcemgmt.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Task entities and DTOs
 */
@Component
public class TaskMapper {

    public TaskDto taskToTaskDto(Task task) {
        if (task == null) {
            return null;
        }
        
        return new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getPriority(),
            task.getAssignedStaffId(),
            task.getAssignedStaffName(),
            task.getStartDate(),
            task.getDueDate(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
            task.getCreatedBy(),
            task.getCustomerReference()
        );
    }

    public List<TaskDto> tasksToTaskDtos(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }
        
        return tasks.stream()
                .map(this::taskToTaskDto)
                .collect(Collectors.toList());
    }

    public TaskDetailsDto taskToTaskDetailsDto(Task task) {
        if (task == null) {
            return null;
        }
        
        return new TaskDetailsDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getPriority(),
            task.getAssignedStaffId(),
            task.getAssignedStaffName(),
            task.getStartDate(),
            task.getDueDate(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
            task.getCreatedBy(),
            task.getCustomerReference(),
            task.getActivityHistory(),
            task.getComments()
        );
    }
}
