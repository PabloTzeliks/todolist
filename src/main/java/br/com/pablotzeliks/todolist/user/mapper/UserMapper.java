package br.com.pablotzeliks.todolist.user.mapper;

import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável pela conversão entre DTOs e Entidades de Usuário.
 * <p>
 * Esta classe implementa o padrão Mapper (ou Converter), isolando a lógica de
 * transformação de objetos em um componente dedicado. Ela garante que a conversão
 * entre DTOs e entidades seja feita de forma consistente e centralizada, facilitando
 * manutenção e evolução do código.
 * </p>
 * <p>
 * A conversão manual permite controle total sobre quais campos são mapeados,
 * sendo especialmente importante para evitar exposição de dados sensíveis como
 * a senha criptografada nos DTOs de resposta.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see UserRequestDTO
 * @see UserResponseDTO
 * @see User
 */
@Component
public class UserMapper {

    /**
     * Converte uma entidade User em UserResponseDTO.
     * <p>
     * Este método mapeia apenas os campos seguros da entidade para o DTO de resposta,
     * excluindo intencionalmente a senha criptografada para evitar vazamento de
     * informações sensíveis.
     * </p>
     *
     * @param user Entidade de usuário a ser convertida
     * @return DTO de resposta com os dados seguros do usuário, ou null se a entidade for null
     */
    public UserResponseDTO toResponse(User user) {

        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }

    /**
     * Converte um UserRequestDTO em uma entidade User.
     * <p>
     * Este método cria uma nova instância de User e popula apenas os campos
     * fornecidos no DTO de requisição, exceto a senha, que deve ser tratada
     * pela camada de serviço (hash BCrypt) antes da persistência.
     * </p>
     *
     * @param dto DTO de requisição contendo os dados do usuário
     * @return Nova entidade User com os dados do DTO, ou null se o DTO for null
     */
    public User toEntity(UserRequestDTO dto) {

        if (dto == null) return null;

        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());

        return user;
    }
}