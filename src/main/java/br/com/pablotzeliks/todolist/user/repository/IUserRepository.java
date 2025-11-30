package br.com.pablotzeliks.todolist.user.repository;

import br.com.pablotzeliks.todolist.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositório de acesso a dados para a entidade {@link User}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações CRUD padrão
 * e métodos de consulta personalizados para a tabela {@code tb_user}.
 * O Spring Data JPA gera automaticamente a implementação em tempo de execução.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see User
 * @see JpaRepository
 */
public interface IUserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca um usuário pelo seu nome de usuário (username).
     * <p>
     * Este método é utilizado principalmente para autenticação e validação
     * de duplicidade durante o cadastro de novos usuários.
     * </p>
     *
     * @param username o nome de usuário a ser buscado
     * @return o usuário encontrado ou {@code null} se não existir
     */
    User findByUsername(String username);
}
