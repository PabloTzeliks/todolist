package br.com.pablotzeliks.todolist.exception.general;

/**
 * Exceção de negócio para tentativas de criação de recursos duplicados.
 * <p>
 * Lançada quando se tenta criar um recurso que já existe no sistema, como um
 * usuário com username duplicado. Tratada pelo {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler}
 * que retorna HTTP 409 (Conflict).
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
