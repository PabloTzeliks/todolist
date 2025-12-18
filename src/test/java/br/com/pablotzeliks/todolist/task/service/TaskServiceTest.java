package br.com.pablotzeliks.todolist.task.service;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.mapper.TaskMapper;
import br.com.pablotzeliks.todolist.task.model.Priority;
import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private ITaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Test if TaskService is correctly creating a Task. Should successfully save the Task when everything is correct.")
    void createTaskSuccessfully_Test() {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        UUID userId = UUID.randomUUID();

        // Create a TaskRequestDTO with test data
        TaskRequestDTO requestDTO = new TaskRequestDTO(
                "Test task",
                "This is a test task",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                Priority.HIGH
        );

        // Create a Task entity that simulates the entity before saving
        Task taskEntity = new Task();
        taskEntity.setUserId(userId);
        taskEntity.setTitle(requestDTO.title());
        taskEntity.setDescription(requestDTO.description());
        taskEntity.setStartAt(requestDTO.startAt());
        taskEntity.setEndAt(requestDTO.endAt());
        taskEntity.setPriority(requestDTO.priority());

        // Create a Task object that simulates the saved entity
        Task taskSaved = new Task();
        taskSaved.setId(UUID.randomUUID());
        taskSaved.setUserId(userId);
        taskSaved.setTitle(requestDTO.title());
        taskSaved.setDescription(requestDTO.description());
        taskSaved.setStartAt(requestDTO.startAt());
        taskSaved.setEndAt(requestDTO.endAt());
        taskSaved.setPriority(requestDTO.priority());

        // Create a Task response DTO that simulates the expected response
        TaskResponseDTO expectedResponse = new TaskResponseDTO(
                taskSaved.getId(),
                taskSaved.getTitle(),
                taskSaved.getDescription(),
                taskSaved.getStartAt(),
                taskSaved.getEndAt(),
                taskSaved.getPriority(),
                userId,
                taskSaved.getCreatedAt(),
                taskSaved.getUpdatedAt()
        );

        // Mocking the mapper behavior from RequestDTO to Entity
        when(taskMapper.toEntity(any(TaskRequestDTO.class))).thenReturn(taskEntity);

        // Mocking the repository save method
        when(taskRepository.save(any(Task.class))).thenReturn(taskSaved);

        // Mocking the mapper behavior from Entity to ResponseDTO
        when(taskMapper.toResponse(any(Task.class))).thenReturn(expectedResponse);

        // Act
        TaskResponseDTO responseDTO = taskService.create(requestDTO, userId);

        // Assert
        assertNotNull(responseDTO);

        assertEquals(taskSaved.getId(), responseDTO.id());
        assertEquals(taskSaved.getUserId(), responseDTO.userId());
        assertEquals("Test task", responseDTO.title());
        assertEquals("This is a test task", responseDTO.description());
        assertEquals(taskSaved.getStartAt(), responseDTO.startAt());
        assertEquals(taskSaved.getEndAt(), responseDTO.endAt());
        assertEquals(Priority.HIGH, responseDTO.priority());

        // Verify if all the Methods were called as expected
        verify(taskMapper).toEntity(any(TaskRequestDTO.class));
        verify(taskRepository).save(any(Task.class));
        verify(taskMapper).toResponse(any(Task.class));
    }
}