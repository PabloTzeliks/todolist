package br.com.pablotzeliks.todolist.task.mapper;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponseDTO toResponse(Task task) {

        if (task == null) return null;

        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStartAt(),
                task.getEndAt(),
                task.getPriority(),
                task.getUserId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public Task toEntity(TaskRequestDTO dto) {

        if (dto == null) return null;

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStartAt(dto.startAt());
        task.setEndAt(dto.endAt());
        task.setPriority(dto.priority());

        return task;
    }
}