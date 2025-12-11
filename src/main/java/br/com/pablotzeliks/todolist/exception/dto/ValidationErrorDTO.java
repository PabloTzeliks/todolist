package br.com.pablotzeliks.todolist.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO (Data Transfer Object) imutável para detalhamento de erros de validação.
 * <p>
 * Este record representa um erro específico de validação de campo, indicando qual
 * campo falhou e por qual motivo. É utilizado dentro do {@link ErrorResponseDTO}
 * para fornecer feedback detalhado ao cliente sobre problemas de validação.
 * </p>
 * <p>
 * Facilita a integração com Frontends, permitindo que erros sejam exibidos
 * ao lado dos campos correspondentes no formulário.
 * </p>
 *
 * @param field   Nome do campo que gerou o erro (ex: "password", "title")
 * @param message Mensagem detalhada do erro de validação
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see ErrorResponseDTO
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
public record ValidationErrorDTO(

        @Schema(description = "Campo que gerou o erro", example = "password")
        String field,

        @Schema(description = "Mensagem detalhada do erro", example = "A senha deve ter entre 6 e 20 caracteres")
        String message
) { }