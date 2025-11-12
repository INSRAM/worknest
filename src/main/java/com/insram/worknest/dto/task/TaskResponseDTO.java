package com.insram.worknest.dto.task;

import com.insram.worknest.model.entities.task.Priority;
import com.insram.worknest.model.entities.task.TaskStatus;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private Long assigneeId;
    private LocalDateTime dueDate;
    private Instant createdAt;
    private Instant updatedAt;

//    private List<CommentDto> comments;
//    private List<AttachmentDto> attachments;
}
