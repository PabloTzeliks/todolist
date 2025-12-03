package br.com.pablotzeliks.todolist.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(

        UUID id,
        String name,
        String username,
        LocalDateTime createdAt
) { }