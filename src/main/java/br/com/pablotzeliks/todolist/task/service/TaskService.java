package br.com.pablotzeliks.todolist.task.service;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.mapper.TaskMapper;
import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository repository;

    @Autowired
    private TaskMapper mapper;

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

    public List<TaskResponseDTO> list(UUID userId) {

        var taskList = repository.findByUserId(userId);

        return taskList.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TaskResponseDTO update(UUID id, TaskRequestDTO taskRequestDTO, UUID userId) {

        Task task = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada."));

        if (!task.getUserId().equals(userId)) {

            throw new IllegalArgumentException("Usuário não tem permissão para acessar essa Tarefa.");
        }

        validatesDate(taskRequestDTO);

        if (taskRequestDTO.title() != null) task.setTitle(taskRequestDTO.title());
        if (taskRequestDTO.description() != null) task.setDescription(taskRequestDTO.description());
        if (taskRequestDTO.startAt() != null) task.setStartAt(taskRequestDTO.startAt());
        if (taskRequestDTO.endAt() != null) task.setEndAt(taskRequestDTO.endAt());
        if (taskRequestDTO.priority() != null) task.setPriority(taskRequestDTO.priority());

        task.setUpdatedAt(LocalDateTime.now());

        Task persistencyTask = repository.save(task);

        return mapper.toResponse(persistencyTask);
    }

    public void validatesDate(TaskRequestDTO requestDTO) {

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(requestDTO.startAt()) || currentDate.isAfter(requestDTO.endAt())) {

            throw new IllegalArgumentException("Data inicial / final deve ser futura.");
        }

        if (requestDTO.startAt().isAfter(requestDTO.endAt())) {

            throw new IllegalArgumentException("Data inicial não pode ser posterior a final.");
        }

        if (requestDTO.startAt().isEqual(requestDTO.endAt())) {

            throw new IllegalArgumentException("Data inicial não pode igual a final.");
        }
    }

}