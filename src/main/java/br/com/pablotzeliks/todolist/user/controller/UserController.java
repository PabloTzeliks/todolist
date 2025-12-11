package br.com.pablotzeliks.todolist.user.controller;

import br.com.pablotzeliks.todolist.exception.dto.ErrorResponseDTO;
import br.com.pablotzeliks.todolist.user.dto.UserRequestDTO;
import br.com.pablotzeliks.todolist.user.dto.UserResponseDTO;
import br.com.pablotzeliks.todolist.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
 * @see UserService
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Service para operações lógicas e de regra de négocio para os usuários.
     * Injetado automaticamente pelo Spring através de {@code @Autowired}.
     */
    @Autowired
    private UserService service;

    @Operation(summary = "Registra um novo usuário", description = "Cria uma conta de usuário com senha criptografada")
    @ApiResponse(responseCode = "201", description = "Sucesso")
    @ApiResponse(responseCode = "409", description = "Conflito: Usuário já existe",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody UserRequestDTO requestDTO) {

        UserResponseDTO user = service.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
