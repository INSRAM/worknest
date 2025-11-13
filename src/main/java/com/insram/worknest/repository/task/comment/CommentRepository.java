package com.insram.worknest.repository.task.comment;

import com.insram.worknest.model.entities.task.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
