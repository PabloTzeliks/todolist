package br.com.pablotzeliks.todolist.user.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pablotzeliks.todolist.user.model.User;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável pelo gerenciamento de usuários.
 * <p>
 * Esta classe expõe endpoints para operações relacionadas a usuários,
 * como criação de novas contas. Todos os endpoints são prefixados com {@code /users}.
 * </p>
 * <p>
 * A anotação {@code @RestController} combina {@code @Controller} e {@code @ResponseBody},
 * indicando que os métodos retornam dados diretamente no corpo da resposta HTTP.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see User
 * @see IUserRepository
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Repositório para operações de persistência de usuários.
     * Injetado automaticamente pelo Spring através de {@code @Autowired}.
     */
    @Autowired
    private IUserRepository repository;

    /**
     * Cria um novo usuário no sistema.
     * <p>
     * Este endpoint realiza as seguintes validações e operações:
     * </p>
     * <ol>
     *   <li>Verifica se já existe um usuário com o mesmo username.</li>
     *   <li>Gera um hash BCrypt da senha com custo 12 para armazenamento seguro.</li>
     *   <li>Persiste o novo usuário no banco de dados.</li>
     * </ol>
     *
     * @param user objeto contendo os dados do usuário a ser criado (name, username, password)
     * @return {@link ResponseEntity} contendo:
     *         <ul>
     *           <li>HTTP 200 (OK) com o usuário criado em caso de sucesso</li>
     *           <li>HTTP 400 (Bad Request) se o username já estiver em uso</li>
     *         </ul>
     */
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody User user) {

        if (repository.findByUsername(user.getUsername()) != null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

        user.setPassword(passwordHashed);

        var userCreated = repository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
