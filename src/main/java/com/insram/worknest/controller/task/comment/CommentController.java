package com.insram.worknest.controller.task.comment;

import com.insram.worknest.dto.task.comment.CommentCreateDto;
import com.insram.worknest.dto.task.comment.CommentResponseDto;
import com.insram.worknest.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentCreateDto dto) {

        CommentResponseDto response = commentService.addComment(taskId, dto);
        return ResponseEntity.ok(response);
    }
}
