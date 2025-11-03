package com.insram.worknest.model.role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

