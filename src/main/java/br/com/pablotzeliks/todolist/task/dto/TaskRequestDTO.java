package br.com.pablotzeliks.todolist.task.dto;

import br.com.pablotzeliks.todolist.task.model.Priority;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) imutável para requisições de criação e atualização de tarefas.
 * <p>
 * Este record representa um objeto de transferência de dados utilizado para receber
 * informações de tarefas vindas do cliente (via HTTP). Por ser um DTO, ele isola a
 * camada de apresentação da camada de persistência, evitando a exposição direta das
 * entidades JPA e garantindo maior segurança e flexibilidade.
 * </p>
 * <p>
 * Utiliza anotações do Bean Validation (Hibernate Validator) para validação declarativa
 * dos campos, incluindo {@code @NotBlank}, {@code @Size}, {@code @NotNull}, 
 * {@code @FutureOrPresent} e {@code @Future}, garantindo integridade dos dados de entrada.
 * </p>
 *
 * @param title       Título da tarefa (obrigatório, máximo 50 caracteres)
 * @param description Descrição da tarefa (opcional, máximo 255 caracteres)
 * @param startAt     Data/hora de início (obrigatória, presente ou futura)
 * @param endAt       Data/hora de término (obrigatória, futura)
 * @param priority    Prioridade da tarefa (enum Priority)
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.task.model.Task
 * @see br.com.pablotzeliks.todolist.task.mapper.TaskMapper
 */
public record TaskRequestDTO(

        @NotBlank(message = "You must insert a title")
        @Size(max = 50, message = "Title must not pass from 50 characters")
        String title,

        @Size(max = 255, message = "Description must not pass from 255 characters")
        String description,

        @NotNull(message = "You must insert a start date")
        @FutureOrPresent(message = "Start date must not be from past only from present or past")
        LocalDateTime startAt,

        @NotNull(message = "You must insert an end date")
        @Future(message = "End date must not be from past or present only from future")
        LocalDateTime endAt,

        Priority priority
) { }