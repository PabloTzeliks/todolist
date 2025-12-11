package br.com.pablotzeliks.todolist.exception.general;

/**
 * Exceção de negócio para violações de regras de domínio.
 * <p>
 * Lançada quando uma operação viola uma regra de negócio da aplicação, como
 * datas inválidas, valores fora do intervalo permitido, etc. Tratada pelo
 * {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler} que
 * retorna HTTP 400 (Bad Request).
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
