package br.com.pablotzeliks.todolist.task.dto;

import br.com.pablotzeliks.todolist.task.model.Priority;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(

        UUID id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Priority priority,
        UUID userId,
        LocalDateTime createdAt
) { }