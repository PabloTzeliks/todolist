package br.com.pablotzeliks.todolist.task.dto;

import br.com.pablotzeliks.todolist.task.model.Priority;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TaskRequestDTO(

        @NotBlank(message = "You must insert a title")
        @Size(max = 50, message = "Title must not pass from 50 characters")
        String title,

        @Size(max = 255, message = "Description must not pass from 255 characters")
        String description,

        @NotNull(message = "You must insert a start date")
        @FutureOrPresent(message = "Start date must not be from past only from present or past")
        LocalDateTime startAt,

        @NotNull(message = "You must insert an end date")
        @Future(message = "End date must not be from past or present only from future")
        LocalDateTime endAt,

        Priority priority
) { }