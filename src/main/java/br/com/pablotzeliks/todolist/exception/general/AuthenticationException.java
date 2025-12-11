package br.com.pablotzeliks.todolist.exception.general;

/**
 * Exceção de negócio para erros de autenticação.
 * <p>
 * Lançada quando credenciais inválidas são fornecidas ou quando o token de
 * autenticação está ausente. Tratada pelo {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler}
 * que retorna HTTP 401 (Unauthorized).
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
