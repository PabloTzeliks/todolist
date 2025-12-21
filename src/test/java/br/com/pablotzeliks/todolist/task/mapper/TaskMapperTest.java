package br.com.pablotzeliks.todolist.task.mapper;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.model.Priority;
import br.com.pablotzeliks.todolist.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper mapper = new TaskMapper();

    @Test
    @DisplayName("Should convert TaskRequestDTO to Task Entity correctly")
    void toEntity_Test() {

        // Arrange
        TaskRequestDTO requestDTO = new TaskRequestDTO(
                "Test Title",
                "Test Description",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                Priority.HIGH
        );

        // Act
        Task resultEntity = mapper.toEntity(requestDTO);

        // Assert
        assertNotNull(resultEntity);
        assertEquals(requestDTO.title(), resultEntity.getTitle());
        assertEquals(requestDTO.description(), resultEntity.getDescription());
        assertEquals(requestDTO.priority(), resultEntity.getPriority());
        assertEquals(requestDTO.startAt(), resultEntity.getStartAt());
        assertEquals(requestDTO.endAt(), resultEntity.getEndAt());

        assertNull(resultEntity.getId());
    }

    @Test
    @DisplayName("Should convert Task Entity to TaskResponseDTO correctly")
    void toResponse_Test() {

        // Arrange
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Task entity = new Task();
        entity.setId(id);
        entity.setUserId(userId);
        entity.setTitle("Entity Title");
        entity.setDescription("Entity Description");
        entity.setPriority(Priority.LOW);
        entity.setStartAt(now.plusDays(1));
        entity.setEndAt(now.plusDays(2));
        entity.setCreatedAt(now);

        // Act
        TaskResponseDTO resultDTO = mapper.toResponse(entity);

        // Assert
        assertNotNull(resultDTO);
        assertEquals(entity.getId(), resultDTO.id());
        assertEquals(entity.getUserId(), resultDTO.userId());
        assertEquals(entity.getTitle(), resultDTO.title());
        assertEquals(entity.getDescription(), resultDTO.description());
        assertEquals(entity.getPriority(), resultDTO.priority());
        assertEquals(entity.getStartAt(), resultDTO.startAt());
        assertEquals(entity.getEndAt(), resultDTO.endAt());
        assertEquals(entity.getCreatedAt(), resultDTO.createdAt());
    }
}