package br.com.pablotzeliks.todolist.task.service;

import br.com.pablotzeliks.todolist.exception.general.BusinessRuleException;
import br.com.pablotzeliks.todolist.exception.general.ResourceNotFoundException;
import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskUpdateDTO;
import br.com.pablotzeliks.todolist.task.mapper.TaskMapper;
import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import br.com.pablotzeliks.todolist.user.exception.UserNotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Camada de serviço responsável pelas regras de negócio relacionadas a Tarefas.
 * <p>
 * Esta classe concentra toda a lógica de negócio da aplicação para o domínio de tarefas,
 * incluindo validações, transformações de dados e orquestração de operações. Ela NÃO
 * retorna mais {@link org.springframework.http.ResponseEntity}, mas sim DTOs ou lança
 * exceções semânticas que são tratadas pelo {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler}.
 * </p>
 * <p>
 * Seguindo o princípio de Single Responsibility, o Service delega a conversão de objetos
 * ao {@link TaskMapper} e a persistência ao {@link br.com.pablotzeliks.todolist.task.repository.ITaskRepository},
 * mantendo-se focado nas regras de negócio.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see TaskRequestDTO
 * @see TaskResponseDTO
 * @see TaskMapper
 * @see br.com.pablotzeliks.todolist.task.repository.ITaskRepository
 */
@Service
public class TaskService {

    @Autowired
    private ITaskRepository repository;

    @Autowired
    private TaskMapper mapper;

    /**
     * Cria uma nova tarefa no sistema.
     * <p>
     * Este método valida as datas fornecidas, converte o DTO em entidade,
     * associa a tarefa ao usuário autenticado e persiste no banco de dados.
     * </p>
     *
     * @param taskRequestDTO DTO contendo os dados da tarefa a ser criada
     * @param userId         Identificador do usuário autenticado
     * @return DTO de resposta com os dados da tarefa criada
     * @throws BusinessRuleException se as datas forem inválidas
     */
    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO, UUID userId) {

        // DateTime Validation
        validatesDate(taskRequestDTO);

        // DTO to Entity
        Task entity = mapper.toEntity(taskRequestDTO);
        entity.setUserId(userId);

        // Persistency
        Task persistencyTask = repository.save(entity);

        // Map to Response DTO
        return mapper.toResponse(persistencyTask);
    }

    /**
     * Lista todas as tarefas de um usuário específico.
     * <p>
     * Retorna uma lista vazia caso o usuário não possua tarefas cadastradas.
     * </p>
     *
     * @param userId Identificador do usuário autenticado
     * @return Lista de DTOs de resposta com as tarefas do usuário
     */
    public List<TaskResponseDTO> list(UUID userId) {

        var taskList = repository.findByUserId(userId);

        return taskList.stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Atualiza uma tarefa existente no sistema.
     * <p>
     * Este método verifica se a tarefa existe, valida a propriedade do usuário,
     * aplica validações de datas e atualiza apenas os campos fornecidos no DTO.
     * Utiliza o padrão de atualização parcial (PATCH-like), permitindo que o
     * cliente envie apenas os campos que deseja alterar.
     * </p>
     *
     * @param id             Identificador da tarefa a ser atualizada
     * @param taskRequestDTO DTO contendo os dados a serem atualizados
     * @param userId         Identificador do usuário autenticado
     * @return DTO de resposta com os dados da tarefa atualizada
     * @throws ResourceNotFoundException     se a tarefa não for encontrada
     * @throws UserNotAuthorizedException    se o usuário não for o proprietário da tarefa
     * @throws BusinessRuleException         se as datas forem inválidas
     */
    public TaskResponseDTO update(UUID id, TaskUpdateDTO taskRequestDTO, UUID userId) {

        Task task = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada."));

        if (!task.getUserId().equals(userId)) {

            throw new UserNotAuthorizedException("Usuário não tem permissão para acessar essa Tarefa.");
        }

        validatesDateForUpdate(task, taskRequestDTO);

        if (taskRequestDTO.title() != null) task.setTitle(taskRequestDTO.title());
        if (taskRequestDTO.description() != null) task.setDescription(taskRequestDTO.description());
        if (taskRequestDTO.startAt() != null) task.setStartAt(taskRequestDTO.startAt());
        if (taskRequestDTO.endAt() != null) task.setEndAt(taskRequestDTO.endAt());
        if (taskRequestDTO.priority() != null) task.setPriority(taskRequestDTO.priority());

        var currentDate = LocalDateTime.now();

        task.setUpdatedAt(currentDate);

        Task persistencyTask = repository.save(task);

        return mapper.toResponse(persistencyTask);
    }

    private void validatesDate(TaskRequestDTO requestDTO) {

        if (requestDTO.startAt().isAfter(requestDTO.endAt())) {

            throw new BusinessRuleException("Data inicial não pode ser posterior a final.");
        }

        if (requestDTO.startAt().isEqual(requestDTO.endAt())) {

            throw new BusinessRuleException("Data inicial não pode igual a final.");
        }
    }

    private void validatesDateForUpdate(Task task, TaskUpdateDTO updateRequestDTO) {

        LocalDateTime startAt = updateRequestDTO.startAt() != null ? updateRequestDTO.startAt() : task.getStartAt();
        LocalDateTime endAt = updateRequestDTO.endAt() != null ? updateRequestDTO.endAt() : task.getEndAt();

        if (startAt.isAfter(endAt)) {

            throw new BusinessRuleException("Data inicial não pode ser posterior a final.");
        }

        if (startAt.isEqual(endAt)) {

            throw new BusinessRuleException("Data inicial não pode igual a final.");
        }
    }
}