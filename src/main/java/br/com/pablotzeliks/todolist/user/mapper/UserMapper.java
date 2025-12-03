package br.com.pablotzeliks.todolist.user.mapper;

import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponse(User user) {

        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }

    public User toEntity(UserRequestDTO dto) {

        if (dto == null) return null;

        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());

        return user;
    }
}