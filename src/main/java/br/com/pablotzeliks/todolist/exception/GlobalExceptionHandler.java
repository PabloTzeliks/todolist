package br.com.pablotzeliks.todolist.exception;

import br.com.pablotzeliks.todolist.exception.dto.ErrorResponseDTO;
import br.com.pablotzeliks.todolist.exception.dto.ValidationErrorDTO;
import br.com.pablotzeliks.todolist.exception.general.BusinessRuleException;
import br.com.pablotzeliks.todolist.exception.general.ResourceAlreadyExistsException;
import br.com.pablotzeliks.todolist.exception.general.ResourceNotFoundException;
import br.com.pablotzeliks.todolist.exception.general.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.List;

/**
 * Controlador global de tratamento de exceções da aplicação.
 * <p>
 * Esta classe utiliza a anotação {@code @ControllerAdvice} para interceptar e tratar
 * exceções lançadas por qualquer controlador da aplicação, centralizando o tratamento
 * de erros e fornecendo respostas HTTP padronizadas conforme a <strong>RFC 7807</strong>
 * (Problem Details for HTTP APIs).
 * </p>
 * <p>
 * <strong>Benefícios da centralização:</strong>
 * <ul>
 *   <li>Elimina duplicação de código de tratamento de erros nos Controllers</li>
 *   <li>Garante respostas consistentes em toda a API</li>
 *   <li>Facilita integração com clientes ao fornecer estrutura JSON padronizada</li>
 *   <li>Suporta lista de erros de validação detalhados (campo + mensagem)</li>
 * </ul>
 * </p>
 * <p>
 * Para erros de validação ({@code MethodArgumentNotValidException}), retorna um JSON
 * com a lista {@code errors}, permitindo que o Frontend exiba erros específicos ao
 * lado de cada campo do formulário.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see ErrorResponseDTO
 * @see ValidationErrorDTO
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMostSpecificCause().getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        // Devolve uma Lista de DTO para Erros de Validação, retornando um JSON de objetos de Erros personalizados
        List<ValidationErrorDTO> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationErrorDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Error on Field Validation",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Um erro inesperado ocorreu. Contate o Suporte.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
