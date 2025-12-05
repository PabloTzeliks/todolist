package br.com.pablotzeliks.todolist.exception.general;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
