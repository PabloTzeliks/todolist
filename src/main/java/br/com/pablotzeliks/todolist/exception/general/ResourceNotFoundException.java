package br.com.pablotzeliks.todolist.exception.general;

/**
 * Exceção de negócio para recursos não encontrados.
 * <p>
 * Lançada quando um recurso solicitado não existe no sistema, como uma tarefa
 * com ID inexistente. Tratada pelo {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler}
 * que retorna HTTP 404 (Not Found).
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
