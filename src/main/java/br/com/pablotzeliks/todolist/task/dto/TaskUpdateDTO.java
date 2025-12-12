package br.com.pablotzeliks.todolist.task.dto;

import br.com.pablotzeliks.todolist.task.model.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskUpdateDTO(

        @Size(max = 50, message = "Title must not pass from 50 characters")
        String title,

        @Size(max = 255, message = "Description must not pass from 255 characters")
        String description,

        @FutureOrPresent(message = "Start date must not be from the past")
        LocalDateTime startAt,

        @Future(message = "End date must be in the future")
        LocalDateTime endAt,

        Priority priority
) { }