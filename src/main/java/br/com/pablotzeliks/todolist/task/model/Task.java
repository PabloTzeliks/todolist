package br.com.pablotzeliks.todolist.task.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID userId;

    @Column(length = 50)
    private String title;

    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
