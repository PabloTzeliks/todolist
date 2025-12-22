package br.com.pablotzeliks.todolist.user.service;

import br.com.pablotzeliks.todolist.exception.general.ResourceAlreadyExistsException;
import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.mapper.UserMapper;
import br.com.pablotzeliks.todolist.user.model.User;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository repository;

    @Mock
    private UserMapper mapper;

    @Test
    @DisplayName("Should create user successfully, encrypt password and return response DTO")
    void createUser_Success_Test() {

        // Arrange
        String rawPassword = "123456";
        UserRequestDTO requestDTO = new UserRequestDTO("pablo", "Pablo Tzeliks", rawPassword);

        User userEntity = new User();
        userEntity.setUsername("pablo");
        userEntity.setName("Pablo Tzeliks");

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setUsername("pablo");
        savedUser.setPassword("$2a$12$..."); // Simulando hash
        savedUser.setCreatedAt(LocalDateTime.now());

        UserResponseDTO expectedResponse = new UserResponseDTO(
                savedUser.getId(), "pablo", "Pablo Tzeliks", savedUser.getCreatedAt()
        );

        // Mocks definition
        // Test if no one has this username
        when(repository.findByUsername(requestDTO.username())).thenReturn(null);

        // Mapper converts DTO -> Entity
        when(mapper.toEntity(requestDTO)).thenReturn(userEntity);

        // Repository save and return the user with ID
        when(repository.save(any(User.class))).thenReturn(savedUser);

        // Mapper converts Entity Saved -> Response DTO
        when(mapper.toResponse(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponseDTO result = userService.create(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.id(), result.id());
        assertEquals(expectedResponse.username(), result.username());

        // Security Assure if the password was hashed

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotEquals(rawPassword, capturedUser.getPassword(), "The password should be hashed");

        assertNotNull(capturedUser.getPassword());
        assertTrue(capturedUser.getPassword().startsWith("$2a$"), "The password should be a valid BCrypt hash");
    }

    @Test
    @DisplayName("Should throw ResourceAlreadyExistsException when username is already taken")
    void createUser_UsernameExists_Test() {

        // Arrange
        UserRequestDTO requestDTO = new UserRequestDTO("pablo", "Pablo Tzeliks", "123456");

        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setUsername("pablo");

        when(repository.findByUsername(requestDTO.username())).thenReturn(existingUser);

        // Act & Assert
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.create(requestDTO);
        });

        assertEquals("Username Pablo Tzeliks já está em uso.", exception.getMessage());

        // Verify
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
    }
}