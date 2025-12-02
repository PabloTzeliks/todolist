package br.com.pablotzeliks.todolist.task.dto;

import br.com.pablotzeliks.todolist.task.model.Priority;

import java.time.LocalDateTime;

public record TaskRequestDTO(

        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Priority priority
) { }