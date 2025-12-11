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
 * de erros e fornecendo respostas HTTP padronizadas.
 * </p>
 * <p>
 * O uso de {@code @ControllerAdvice} permite definir handlers de exceção que se aplicam
 * a todos os controladores, evitando duplicação de código de tratamento de erros.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
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
