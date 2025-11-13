package com.insram.worknest.service.comment;

import com.insram.worknest.dto.task.comment.CommentCreateDto;
import com.insram.worknest.dto.task.comment.CommentResponseDto;
import com.insram.worknest.mapper.task.comment.CommentMapper;
import com.insram.worknest.model.entities.task.Task;
import com.insram.worknest.model.entities.task.comment.Comment;
import com.insram.worknest.repository.task.TaskRepository;
import com.insram.worknest.repository.task.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    // Add Comment
    public CommentResponseDto addComment(Long taskId, CommentCreateDto dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthorId(dto.getAuthorId());
        comment.setText(dto.getText());

        Comment saved = commentRepository.save(comment);
        return commentMapper.toResponseDto(saved);
    }
}
