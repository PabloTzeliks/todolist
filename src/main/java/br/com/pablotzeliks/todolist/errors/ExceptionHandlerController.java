package br.com.pablotzeliks.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
public class ExceptionHandlerController {

    /**
     * Trata exceções de mensagens HTTP não legíveis.
     * <p>
     * Esta exceção ocorre quando o corpo da requisição não pode ser deserializado,
     * geralmente devido a JSON mal formatado ou tipos de dados incompatíveis.
     * Por exemplo, quando uma data inválida é enviada onde uma {@code LocalDateTime} é esperada.
     * </p>
     *
     * @param exception a exceção capturada contendo detalhes do erro de deserialização
     * @return {@link ResponseEntity} com status HTTP 400 (Bad Request) e a mensagem
     *         de erro mais específica da exceção
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMostSpecificCause().getMessage());
    }
}
