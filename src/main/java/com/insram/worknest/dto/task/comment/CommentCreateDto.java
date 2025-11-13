package com.insram.worknest.dto.task.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentCreateDto {

    private Long authorId;

    @NotBlank
    @Size(max = 1000)
    private String text;
}
