package br.com.pablotzeliks.todolist.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.mapper.UserMapper;
import br.com.pablotzeliks.todolist.user.model.User;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    public UserResponseDTO create(UserRequestDTO requestDTO) {

        // Validates the no existency of the User
        var userAlreadyExists = repository.findByUsername(requestDTO.username());

        if (userAlreadyExists != null) {

            throw new IllegalArgumentException("Username já existente.");
        }

        // requestDTO -> Entity
        User user = mapper.toEntity(requestDTO);

        // Hashes the User Password
        var passwordHashed = BCrypt.withDefaults()
                .hashToString(12, requestDTO.password().toCharArray());

        user.setPassword(passwordHashed);

        // Persistency
        User persistencyUser = repository.save(user);

        // Entity -> responseDTO
        return mapper.toResponse(persistencyUser);
    }
}
