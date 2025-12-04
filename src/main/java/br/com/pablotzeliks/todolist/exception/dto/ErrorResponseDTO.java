package br.com.pablotzeliks.todolist.exception.dto;

public record ErrorResponseDTO(

        String message,
        int status,
        String statusError
) { }
