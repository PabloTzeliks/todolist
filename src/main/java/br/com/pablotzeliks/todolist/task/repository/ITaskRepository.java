package br.com.pablotzeliks.todolist.task.repository;

import br.com.pablotzeliks.todolist.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório de acesso a dados para a entidade {@link Task}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações CRUD padrão
 * e métodos de consulta personalizados para a tabela {@code tb_task}.
 * O Spring Data JPA gera automaticamente a implementação em tempo de execução.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see Task
 * @see JpaRepository
 */
public interface ITaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Busca todas as tarefas pertencentes a um usuário específico.
     * <p>
     * Este método é utilizado para listar todas as tarefas de um usuário autenticado.
     * </p>
     *
     * @param userId o identificador único do usuário proprietário das tarefas
     * @return lista de tarefas pertencentes ao usuário, ou lista vazia se não houver tarefas
     */
    List<Task> findByUserId(UUID userId);

    /**
     * Busca uma tarefa específica pelo seu ID e pelo ID do usuário proprietário.
     * <p>
     * Este método garante que um usuário só possa acessar suas próprias tarefas,
     * combinando ambos os critérios na busca. É utilizado principalmente nas
     * operações de atualização para validar a propriedade da tarefa.
     * </p>
     *
     * @param id o identificador único da tarefa
     * @param userId o identificador único do usuário proprietário
     * @return a tarefa encontrada ou {@code null} se não existir ou não pertencer ao usuário
     */
    Task findByIdAndUserId(UUID id, UUID userId);
}
