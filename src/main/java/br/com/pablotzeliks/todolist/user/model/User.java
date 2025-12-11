package br.com.pablotzeliks.todolist.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade que representa um usuário do sistema de gerenciamento de tarefas.
 * <p>
 * Esta classe é mapeada para a tabela {@code tb_user} no banco de dados e contém
 * as informações de autenticação e identificação do usuário. Cada usuário pode
 * possuir múltiplas tarefas associadas ao seu identificador único.
 * </p>
 * <p>
 * A anotação {@code @Data} do Lombok gera automaticamente os métodos getters, setters,
 * {@code toString()}, {@code equals()} e {@code hashCode()}.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.task.model.Task
 */
@Data
@Entity(name = "tb_user")
public class User {

    /**
     * Identificador único do usuário, gerado automaticamente como UUID.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    /**
     * Nome completo do usuário.
     */
    private String name;

    /**
     * Nome de usuário para autenticação. Deve ser único no sistema.
     * <p>
     * A restrição de unicidade é garantida pela anotação {@code @Column(unique = true)}.
     * </p>
     */
    @Column(unique = true)
    private String username;

    /**
     * Senha do usuário armazenada em formato hash BCrypt.
     * <p>
     * A senha nunca é armazenada em texto puro. O hash é gerado durante
     * o processo de criação do usuário.
     * </p>
     */
    private String password;

    /**
     * Data e hora de criação do registro do usuário.
     * <p>
     * Este campo é preenchido automaticamente pelo Hibernate através
     * da anotação {@code @CreationTimestamp}.
     * </p>
     */
    @CreationTimestamp
    private LocalDateTime createdAt;
}
