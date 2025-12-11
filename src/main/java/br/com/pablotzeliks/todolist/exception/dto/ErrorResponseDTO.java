package br.com.pablotzeliks.todolist.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO (Data Transfer Object) imutável para respostas de erro padronizadas.
 * <p>
 * Este record representa a estrutura padrão de respostas de erro da API, seguindo
 * boas práticas como a RFC 7807 (Problem Details for HTTP APIs). Ele fornece informações
 * consistentes sobre erros, facilitando a integração com clientes (Frontend, Mobile, etc).
 * </p>
 * <p>
 * Quando ocorrem erros de validação (Bean Validation), a lista {@code errors} é populada
 * com detalhes específicos de cada campo inválido. Para outros tipos de erro, essa lista
 * é omitida do JSON através da anotação {@code @JsonInclude}.
 * </p>
 *
 * @param message     Mensagem geral do erro
 * @param status      Código HTTP do erro (ex: 400, 404, 500)
 * @param statusError Descrição do status HTTP (ex: "Bad Request", "Not Found")
 * @param errors      Lista detalhada de erros de validação (opcional)
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see ValidationErrorDTO
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
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