package br.com.pablotzeliks.todolist.task.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade que representa uma tarefa no sistema de gerenciamento.
 * <p>
 * Esta classe é mapeada para a tabela {@code tb_task} no banco de dados e contém
 * todas as informações relacionadas a uma tarefa, incluindo título, descrição,
 * datas de início e término, prioridade e relacionamento com o usuário proprietário.
 * </p>
 * <p>
 * A anotação {@code @Data} do Lombok gera automaticamente os métodos getters, setters,
 * {@code toString()}, {@code equals()} e {@code hashCode()}.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see Priority
 * @see br.com.pablotzeliks.todolist.user.model.User
 */
@Data
@Entity(name = "tb_task")
public class Task {

    /**
     * Identificador único da tarefa, gerado automaticamente como UUID.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    /**
     * Identificador do usuário proprietário desta tarefa.
     * Estabelece o relacionamento entre a tarefa e seu criador.
     */
    private UUID userId;

    /**
     * Título da tarefa com limite máximo de 50 caracteres.
     * <p>
     * A restrição de tamanho é aplicada tanto no banco de dados
     * (via {@code @Column(length = 50)}) quanto na validação do setter.
     * </p>
     */
    @Column(length = 50)
    private String title;

    /**
     * Descrição detalhada da tarefa.
     */
    private String description;

    /**
     * Data e hora de início prevista para a tarefa.
     * Deve ser posterior à data atual e anterior à data de término.
     */
    private LocalDateTime startAt;

    /**
     * Data e hora de término prevista para a tarefa.
     * Deve ser posterior à data de início.
     */
    private LocalDateTime endAt;

    /**
     * Nível de prioridade da tarefa.
     * <p>
     * Armazenado como String no banco de dados através de {@code @Enumerated(EnumType.STRING)}.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private Priority priority;

    /**
     * Data e hora de criação do registro da tarefa.
     * <p>
     * Preenchido automaticamente pelo Hibernate através da anotação {@code @CreationTimestamp}.
     * </p>
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Data e hora da última atualização da tarefa.
     * <p>
     * Este campo é atualizado manualmente durante operações de update.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * Define o título da tarefa com validação de tamanho.
     * <p>
     * Este método sobrescreve o setter padrão gerado pelo Lombok para
     * aplicar a regra de negócio que limita o título a 50 caracteres.
     * </p>
     *
     * @param title o título a ser definido para a tarefa
     * @throws IllegalArgumentException se o título ultrapassar 50 caracteres
     */
}
