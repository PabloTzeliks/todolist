package br.com.pablotzeliks.todolist.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ValidationErrorDTO(

        @Schema(description = "Campo que gerou o erro", example = "password")
        String field,

        @Schema(description = "Mensagem detalhada do erro", example = "A senha deve ter entre 6 e 20 caracteres")
        String message
) { }