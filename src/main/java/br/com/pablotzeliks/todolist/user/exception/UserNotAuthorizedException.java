package br.com.pablotzeliks.todolist.user.exception;

import br.com.pablotzeliks.todolist.exception.general.AuthenticationException;

public class UserNotAuthorizedException extends AuthenticationException {
    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
