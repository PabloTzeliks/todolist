package br.com.pablotzeliks.todolist.task.service;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository repository;

    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO, UUID userId) {

        // DateTime Validation
        validatesDate(taskRequestDTO);

        // DTO to Entity
        Task entity = new Task();
        entity.setUserId(userId);
        entity.setTitle(taskRequestDTO.title());
        entity.setDescription(taskRequestDTO.description());
        entity.setStartAt(taskRequestDTO.startAt());
        entity.setEndAt(taskRequestDTO.endAt());
        entity.setPriority(taskRequestDTO.priority());

        // Persistency
        Task persistencyTask = repository.save(entity);

        // Map to Response DTO
        TaskResponseDTO responseDTO = new TaskResponseDTO(
                persistencyTask.getId(),
                persistencyTask.getTitle(),
                persistencyTask.getDescription(),
                persistencyTask.getStartAt(),
                persistencyTask.getEndAt(),
                persistencyTask.getPriority(),
                persistencyTask.getUserId(),
                persistencyTask.getCreatedAt(),
                persistencyTask.getUpdatedAt()
        );

        return responseDTO;
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