package br.com.pablotzeliks.todolist.user.mapper;

import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    @DisplayName("toResponse - Should map User Entity to ResponseDTO excluding password")
    void toResponse_Success() {

        // Arrange
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        User entity = new User();
        entity.setId(id);
        entity.setName("Pablo Tzeliks");
        entity.setUsername("pablo");
        entity.setPassword("$2a$12$hashedpasswordHere...");
        entity.setCreatedAt(now);

        // Act
        UserResponseDTO response = mapper.toResponse(entity);

        // Assert
        assertNotNull(response);
        assertEquals(entity.getId(), response.id());
        assertEquals(entity.getName(), response.name());
        assertEquals(entity.getUsername(), response.username());
        assertEquals(entity.getCreatedAt(), response.createdAt());
    }

    @Test
    @DisplayName("toResponse - Should return null when input is null")
    void toResponse_NullInput() {

        // Act
        UserResponseDTO response = mapper.toResponse(null);

        // Assert
        assertNull(response, "Should return null to avoid NullPointerException");
    }

    @Test
    @DisplayName("toEntity - Should map UserRequestDTO to User Entity excluding raw password")
    void toEntity_Success() {

        // Arrange
        UserRequestDTO requestDTO = new UserRequestDTO("pablo", "Pablo Tzeliks", "123456");

        // Act
        User entity = mapper.toEntity(requestDTO);

        // Assert
        assertNotNull(entity);
        assertEquals(requestDTO.name(), entity.getName());
        assertEquals(requestDTO.username(), entity.getUsername());

        assertNull(entity.getPassword(), "A senha deve permanecer nula até ser processada pelo Service");

        assertNull(entity.getId());
    }

    @Test
    @DisplayName("toEntity - Should return null when input is null")
    void toEntity_NullInput() {

        // Act
        User entity = mapper.toEntity(null);

        // Assert
        assertNull(entity, "Should return null to avoid NullPointerException");
    }
}