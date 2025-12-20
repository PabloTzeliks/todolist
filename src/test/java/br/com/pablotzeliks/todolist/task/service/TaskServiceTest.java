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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        TaskRequestDTO requestDTO = createValidRequest();
        Task taskSaved = createSavedTask(userId, requestDTO);
        TaskResponseDTO expectedResponse = createResponse(taskSaved);

        // Mocking the mapper behavior from RequestDTO to Entity
        when(taskMapper.toEntity(any(TaskRequestDTO.class))).thenReturn(new Task());

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
        assertEquals("Description", responseDTO.description());
        assertEquals(taskSaved.getStartAt(), responseDTO.startAt());
        assertEquals(taskSaved.getEndAt(), responseDTO.endAt());
        assertEquals(Priority.HIGH, responseDTO.priority());

        // Verify if all the Methods were called as expected
        verify(taskMapper).toEntity(any(TaskRequestDTO.class));
        verify(taskRepository).save(any(Task.class));
        verify(taskMapper).toResponse(any(Task.class));
    }

    @Test
    @DisplayName("Test if TaskService is correctly listing Tasks. Should successfully list the Tasks when everything is correct.")
    void listTaskSuccessfully_Test() {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        var userId = UUID.randomUUID();

        TaskRequestDTO firstTaskRequest = createValidRequest();
        TaskRequestDTO secondTaskRequest = createValidRequest();

        Task firstTaskSaved = createSavedTask(userId, firstTaskRequest);
        Task secondTaskSaved = createSavedTask(userId, secondTaskRequest);

        List<Task> taskFromDB = List.of(firstTaskSaved, secondTaskSaved);

        TaskResponseDTO firstExpectedResponse = createResponse(firstTaskSaved);
        TaskResponseDTO secondExpectedResponse = createResponse(secondTaskSaved);

        // Mocking the repository findByUserId method
        when(taskRepository.findByUserId(userId)).thenReturn(taskFromDB);

        // Mocking the mapper behavior from Entity to ResponseDTO
        when(taskMapper.toResponse(firstTaskSaved)).thenReturn(firstExpectedResponse);
        when(taskMapper.toResponse(secondTaskSaved)).thenReturn(secondExpectedResponse);

        // Act
        List<TaskResponseDTO> resultList = taskService.list(userId);

        // Assert
        assertNotNull(resultList);
        assertEquals(2, resultList.size());

        assertEquals(firstExpectedResponse.id(), resultList.get(0).id());
        assertEquals(secondExpectedResponse.id(), resultList.get(1).id());

        // Verify if all the Methods were called as expected
        verify(taskRepository).findByUserId(userId);

        verify(taskMapper, times(1)).toResponse(firstTaskSaved);
        verify(taskMapper, times(1)).toResponse(secondTaskSaved);
    }

    // Auxiliary methods

    private TaskRequestDTO createValidRequest() {
        return new TaskRequestDTO(
                "Test task", "Description",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                Priority.HIGH
        );
    }

    private Task createSavedTask(UUID userId, TaskRequestDTO requestDTO) {
        Task taskSaved = new Task();
        taskSaved.setId(UUID.randomUUID());
        taskSaved.setUserId(userId);
        taskSaved.setTitle(requestDTO.title());
        taskSaved.setDescription(requestDTO.description());
        taskSaved.setStartAt(requestDTO.startAt());
        taskSaved.setEndAt(requestDTO.endAt());
        taskSaved.setPriority(requestDTO.priority());

        return taskSaved;
    }

    private TaskResponseDTO createResponse(Task task) {
        return new TaskResponseDTO(
                task.getId(), task.getTitle(), task.getDescription(),
                task.getStartAt(), task.getEndAt(), task.getPriority(),
                task.getUserId(), task.getCreatedAt(), task.getUpdatedAt()
        );
    }
}