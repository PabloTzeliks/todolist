package br.com.pablotzeliks.todolist.user.exception;

import br.com.pablotzeliks.todolist.exception.general.AuthenticationException;

/**
 * Exceção de negócio para falhas de autorização de usuários.
 * <p>
 * Lançada quando um usuário tenta acessar ou modificar um recurso que não lhe pertence,
 * ou quando as credenciais fornecidas são inválidas. Estende {@link AuthenticationException}
 * e é tratada pelo {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler}
 * que retorna HTTP 401 (Unauthorized).
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see AuthenticationException
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
public class UserNotAuthorizedException extends AuthenticationException {
    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
