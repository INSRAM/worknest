package com.insram.worknest.dto.task;

import com.insram.worknest.model.entities.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskUpdateDto {

    private Long id; // optional, used to identify the task being updated

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private TaskStatus status; // TODO, INPROGRESS, DONE, etc.

    private Long assigneeId; // User assigned to the task

    private LocalDateTime dueDate; // When the task is due

    private LocalDateTime completedAt; // Optional, set when task is completed
}
