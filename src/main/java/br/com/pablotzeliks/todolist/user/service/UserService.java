package br.com.pablotzeliks.todolist.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pablotzeliks.todolist.exception.general.ResourceAlreadyExistsException;
import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.mapper.UserMapper;
import br.com.pablotzeliks.todolist.user.model.User;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço responsável pelas regras de negócio relacionadas a Usuários.
 * <p>
 * Esta classe concentra toda a lógica de negócio da aplicação para o domínio de usuários,
 * incluindo validações de unicidade, hash de senhas com BCrypt e orquestração de operações.
 * Ela NÃO retorna mais {@link org.springframework.http.ResponseEntity}, mas sim DTOs ou
 * lança exceções semânticas que são tratadas pelo 
 * {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler}.
 * </p>
 * <p>
 * Seguindo o princípio de Single Responsibility, o Service delega a conversão de objetos
 * ao {@link UserMapper} e a persistência ao {@link br.com.pablotzeliks.todolist.user.repository.IUserRepository},
 * mantendo-se focado nas regras de negócio e segurança.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see UserRequestDTO
 * @see UserResponseDTO
 * @see UserMapper
 * @see br.com.pablotzeliks.todolist.user.repository.IUserRepository
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    /**
     * Cria um novo usuário no sistema.
     * <p>
     * Este método valida a unicidade do username, converte o DTO em entidade,
     * aplica hash BCrypt na senha (custo 12) e persiste no banco de dados.
     * A senha nunca é armazenada em texto plano, garantindo segurança.
     * </p>
     *
     * @param requestDTO DTO contendo os dados do usuário a ser criado
     * @return DTO de resposta com os dados do usuário criado (sem a senha)
     * @throws ResourceAlreadyExistsException se o username já estiver em uso
     */
    public UserResponseDTO create(UserRequestDTO requestDTO) {

        // Validates that the User does not exist
        var userAlreadyExists = repository.findByUsername(requestDTO.username());

        if (userAlreadyExists != null) {

            throw new ResourceAlreadyExistsException("Username " + requestDTO.username() + " já está em uso.");
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
