package br.com.pablotzeliks.todolist.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) imutável para respostas de usuários.
 * <p>
 * Este record representa um objeto de transferência de dados utilizado para retornar
 * informações de usuários para o cliente (via HTTP). Ele protege a entidade JPA
 * {@link br.com.pablotzeliks.todolist.user.model.User} de exposição direta, especialmente
 * evitando o vazamento de dados sensíveis como a senha criptografada.
 * </p>
 * <p>
 * A imutabilidade proporcionada pelo record garante que os dados não sejam alterados
 * após a criação do objeto, promovendo segurança e consistência na comunicação entre
 * as camadas da aplicação.
 * </p>
 *
 * @param id        Identificador único do usuário
 * @param name      Nome completo do usuário
 * @param username  Nome de usuário único
 * @param createdAt Data/hora de criação do registro
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.user.model.User
 * @see br.com.pablotzeliks.todolist.user.mapper.UserMapper
 */
public record UserResponseDTO(

        UUID id,
        String name,
        String username,
        LocalDateTime createdAt
) { }