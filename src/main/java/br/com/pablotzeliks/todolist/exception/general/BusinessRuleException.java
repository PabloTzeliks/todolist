package br.com.pablotzeliks.todolist.exception.general;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
