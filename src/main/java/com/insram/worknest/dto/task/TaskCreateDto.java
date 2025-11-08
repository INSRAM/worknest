package com.insram.worknest.dto.task;

import com.insram.worknest.model.entities.task.Priority;
import com.insram.worknest.model.entities.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCreateDto {
    @NotBlank
    @Size(max = 200)
    private String title;
    private String description;
    private Long assignedId;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private Priority priority;

}
