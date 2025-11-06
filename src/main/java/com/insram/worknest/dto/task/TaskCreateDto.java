package com.insram.worknest.dto.task;

import com.insram.worknest.model.entities.task.Priority;
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
    private Long assigneeId;
    private LocalDateTime dueDate;
    private Priority priority;

}
