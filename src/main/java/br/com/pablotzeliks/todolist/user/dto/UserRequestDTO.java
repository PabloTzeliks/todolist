package br.com.pablotzeliks.todolist.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) imutável para requisições de criação de usuários.
 * <p>
 * Este record representa um objeto de transferência de dados utilizado para receber
 * informações de novos usuários vindas do cliente (via HTTP). Ele isola a camada de
 * apresentação da entidade JPA {@link br.com.pablotzeliks.todolist.user.model.User},
 * evitando a exposição direta de dados sensíveis e permitindo controle fino sobre
 * os campos aceitos na API.
 * </p>
 * <p>
 * Utiliza anotações do Bean Validation (Hibernate Validator) para validação declarativa
 * dos campos, incluindo {@code @NotBlank} para campos obrigatórios e {@code @Size} para
 * restrições de tamanho, garantindo que apenas dados válidos sejam processados.
 * </p>
 *
 * @param name     Nome completo do usuário (obrigatório)
 * @param username Nome de usuário único (obrigatório)
 * @param password Senha do usuário (obrigatória, entre 6 e 20 caracteres)
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see br.com.pablotzeliks.todolist.user.model.User
 * @see br.com.pablotzeliks.todolist.user.mapper.UserMapper
 */
public record UserRequestDTO(

        @NotBlank(message = "You must insert a name")
        String name,

        @NotBlank(message = "You must insert a username")
        String username,

        @NotBlank(message = "You must insert a Password")
        @Size(min = 6, max = 20, message = "The password must have between 6 and 20 characters")
        String password
) { }