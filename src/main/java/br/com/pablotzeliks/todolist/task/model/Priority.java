package br.com.pablotzeliks.todolist.task.model;

/**
 * Enumeração que representa os níveis de prioridade de uma tarefa.
 * <p>
 * Esta enumeração define quatro níveis distintos de prioridade que podem
 * ser atribuídos às tarefas do sistema, permitindo aos usuários organizar
 * e filtrar suas atividades por ordem de importância.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see Task
 */
public enum Priority {

    /**
     * Prioridade baixa - tarefas que podem ser realizadas quando houver tempo disponível.
     */
    LOW,

    /**
     * Prioridade média - tarefas com importância moderada que devem ser concluídas em prazo razoável.
     */
    MEDIUM,

    /**
     * Prioridade alta - tarefas importantes que requerem atenção prioritária.
     */
    HIGH,

    /**
     * Prioridade urgente - tarefas críticas que devem ser tratadas imediatamente.
     */
    URGENT
}
