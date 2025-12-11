package br.com.pablotzeliks.todolist.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Estrutura padrão de resposta de erros da API")
public record ErrorResponseDTO(

        @Schema(description = "Mensagem geral do erro", example = "Erro na validação dos campos")
        String message,

        @Schema(description = "Código HTTP do erro", example = "400")
        int status,

        @Schema(description = "Descrição do status HTTP", example = "Bad Request")
        String statusError,

        @Schema(description = "Lista detalhada de erros (opcional)")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<ValidationErrorDTO> errors
) {
    public ErrorResponseDTO(String message, int status, String statusError) {
        this(message, status, statusError, null);
    }
}