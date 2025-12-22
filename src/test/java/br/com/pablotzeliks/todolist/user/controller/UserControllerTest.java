package br.com.pablotzeliks.todolist.user.controller;

import br.com.pablotzeliks.todolist.exception.general.ResourceAlreadyExistsException;
import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.service.UserService;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /users/create - Should return 201 Created with valid data")
    void createUser_Return201() throws Exception {

        // Arrange
        UserRequestDTO request = new UserRequestDTO("pablo", "Pablo Tzeliks", "123456");

        UserResponseDTO response = new UserResponseDTO(
                UUID.randomUUID(),
                "pablo",
                "Pablo Tzeliks",
                LocalDateTime.now()
        );

        when(userService.create(any(UserRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Espera 201
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("pablo"))
                .andExpect(jsonPath("$.name").value("Pablo Tzeliks"));
    }

    @Test
    @DisplayName("POST /users/create - Should return 400 Bad Request when fields are invalid")
    void createUser_Return400_Validation() throws Exception {

        // Arrange
        UserRequestDTO invalidRequest = new UserRequestDTO("", "", "12");

        // Act & Assert
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest()); // The @Valid will break before going to Service
    }

    @Test
    @DisplayName("POST /users/create - Should return 409 Conflict (or 400) when user already exists")
    void createUser_ReturnConflict_WhenExists() throws Exception {

        // Arrange
        UserRequestDTO request = new UserRequestDTO("pablo", "Pablo Tzeliks", "123456");

        when(userService.create(any(UserRequestDTO.class)))
                .thenThrow(new ResourceAlreadyExistsException("Username pablo já está em uso."));

        // Act & Assert
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict()) // <--- Verifique seu ExceptionHandler
                .andExpect(jsonPath("$.message").exists());
    }
}