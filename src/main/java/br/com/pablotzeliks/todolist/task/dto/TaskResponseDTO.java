package br.com.pablotzeliks.todolist.task.dto;

import br.com.pablotzeliks.todolist.task.model.Priority;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) imutável para respostas de tarefas.
 * <p>
 * Este record representa um objeto de transferência de dados utilizado para retornar
 * informações de tarefas para o cliente (via HTTP). Por ser um DTO, ele protege a
 * entidade JPA {@link br.com.pablotzeliks.todolist.task.model.Task} de exposição direta,
 * evitando problemas de segurança, serialização e acoplamento indesejado.
 * </p>
 * <p>
 * A imutabilidade proporcionada pelo record garante que os dados não sejam alterados
 * após a criação do objeto, promovendo segurança em ambientes multithread e clareza
 * na comunicação entre as camadas.
 * </p>
 *
 * @param id          Identificador único da tarefa
 * @param title       Título da tarefa
 * @param description Descrição da tarefa
 * @param startAt     Data/hora de início
 * @param endAt       Data/hora de término
 * @param priority    Prioridade da tarefa
 * @param userId      Identificador do usuário proprietário
 * @param createdAt   Data/hora de criação do registro
 * @param updatedAt   Data/hora da última atualização
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.task.model.Task
 * @see br.com.pablotzeliks.todolist.task.mapper.TaskMapper
 */
public record TaskResponseDTO(

        UUID id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Priority priority,
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }