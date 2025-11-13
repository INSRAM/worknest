package com.insram.worknest.dto.task.comment;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentResponseDto {
    private Long id;
    private Long taskId;
    private Long authorId;
    private String text;
    private Instant createdAt;
}
