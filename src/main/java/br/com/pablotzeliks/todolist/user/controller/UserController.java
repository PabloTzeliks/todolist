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
 * Esta classe funciona exclusivamente como <strong>porta de entrada HTTP</strong>,
 * delegando toda a lógica de negócio para a camada {@link UserService}. Ela não
 * contém regras de negócio, validações complexas ou acesso direto ao banco de dados,
 * seguindo os princípios da arquitetura em camadas.
 * </p>
 * <p>
 * Utiliza anotações do Swagger/OpenAPI ({@code @Operation}, {@code @ApiResponse})
 * para gerar documentação interativa automática, acessível via Swagger UI. A validação
 * de entrada é feita declarativamente através do Bean Validation ({@code @Valid}).
 * </p>
 * <p>
 * A anotação {@code @RestController} combina {@code @Controller} e {@code @ResponseBody},
 * indicando que os métodos retornam dados diretamente no corpo da resposta HTTP (JSON).
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
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

    /**
     * Endpoint para criação de um novo usuário.
     * <p>
     * Delega a validação dos dados de entrada ao Bean Validation ({@code @Valid})
     * e toda a lógica de criação, incluindo hash de senha, ao {@link UserService}.
     * Retorna HTTP 201 (Created) com o DTO do usuário criado (sem a senha).
     * </p>
     *
     * @param requestDTO DTO validado contendo os dados do usuário
     * @return ResponseEntity com status 201 e o DTO do usuário criado
     */
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
