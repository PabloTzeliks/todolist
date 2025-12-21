package br.com.pablotzeliks.todolist.task.controller;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskUpdateDTO;
import br.com.pablotzeliks.todolist.task.model.Priority;
import br.com.pablotzeliks.todolist.task.service.TaskService;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /tasks/create - Should return 201 Created when creating a new valid Task successfully.")
    void createTaskSuccesfully_Test() throws Exception {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        var userId = UUID.randomUUID();
        TaskRequestDTO request = createValidRequest();
        TaskResponseDTO response = createResponse(UUID.randomUUID(), userId);

        when(taskService.create(any(TaskRequestDTO.class), eq(userId))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .requestAttr("userId", userId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.title").value(response.title()));
    }

    @Test
    @DisplayName("POST /tasks/create - Should return 400 Bad Request when fields are invalid")
    void createTask_Return400_WhenInvalid() throws Exception {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        TaskRequestDTO invalidRequest = new TaskRequestDTO(null, null, null, null, null);

        // Act & Assert
        mockMvc.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .requestAttr("userId", UUID.randomUUID()))
                .andExpect(status().isBadRequest()); // O @Valid deve barrar e retornar 400

        verify(taskService, never()).create(any(), any()); // Descomente se quiser ser estrito
    }

    @Test
    @DisplayName("GET /tasks/list - Should return 200 OK and a list of tasks")
    void listTasks_Return200() throws Exception {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        UUID userId = UUID.randomUUID();
        TaskResponseDTO task1 = createResponse(UUID.randomUUID(), userId);
        List<TaskResponseDTO> taskList = List.of(task1);

        when(taskService.list(userId)).thenReturn(taskList);

        // Act & Assert
        mockMvc.perform(get("/tasks/list")
                        .requestAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(task1.id().toString())) // Acessa o primeiro item da lista
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /tasks/list - Should return 200 OK and empty list when no tasks")
    void listTasks_Return200_Empty() throws Exception {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        UUID userId = UUID.randomUUID();
        when(taskService.list(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/tasks/list")
                        .requestAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("PUT /tasks/update/{id} - Should return 200 OK when updated successfully")
    void updateTask_Return200() throws Exception {

        // Triple A pattern: Arrange, Act, Assert

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        TaskUpdateDTO updateDTO = new TaskUpdateDTO("New Title", null, null, null, null);
        TaskResponseDTO responseDTO = createResponse(taskId, userId); // Simulamos que retornou atualizado

        when(taskService.update(eq(taskId), any(TaskUpdateDTO.class), eq(userId))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(put("/tasks/update/{id}", taskId) // Passando ID na URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO))
                        .requestAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()));
    }

    // Auxiliary methods

    private TaskRequestDTO createValidRequest() {
        return new TaskRequestDTO(
                "Test Task", "Description",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                Priority.HIGH
        );
    }

    private TaskResponseDTO createResponse(UUID id, UUID userId) {
        return new TaskResponseDTO(
                id, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                Priority.HIGH, userId, LocalDateTime.now(), null
        );
    }
}