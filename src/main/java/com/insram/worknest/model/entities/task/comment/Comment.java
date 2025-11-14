package com.insram.worknest.model.entities.task.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insram.worknest.model.entities.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Task task;

    private Long authorId;

    @NotBlank
    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String text;

    private Instant createdAt = Instant.now();
}
