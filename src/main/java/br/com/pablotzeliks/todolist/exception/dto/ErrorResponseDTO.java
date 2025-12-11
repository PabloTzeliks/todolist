package br.com.pablotzeliks.todolist.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record ErrorResponseDTO(

        String message,
        int status,
        String statusError,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<ValidationErrorDTO> errors
) {

    public ErrorResponseDTO(String message, int status, String statusError) {

        this (
                message,
                status,
                statusError,
                null
        );
    }
}
