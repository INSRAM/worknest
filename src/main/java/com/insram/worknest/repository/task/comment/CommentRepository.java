package com.insram.worknest.repository.task.comment;

import com.insram.worknest.model.entities.task.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.task WHERE c.task.id = :task_id")
    List<Comment> findByTaskIdWithTask(@Param("task_id") Long task_id);
}
