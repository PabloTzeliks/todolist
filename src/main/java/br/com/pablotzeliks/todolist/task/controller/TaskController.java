package br.com.pablotzeliks.todolist.task.controller;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST responsável pelo gerenciamento de tarefas.
 * <p>
 * Esta classe funciona exclusivamente como <strong>porta de entrada HTTP</strong>,
 * delegando toda a lógica de negócio para a camada {@link TaskService}. Ela não
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
 * @see TaskRequestDTO
 * @see TaskResponseDTO
 * @see TaskService
 * @see br.com.pablotzeliks.todolist.security.FilterTaskAuth
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    /**
     * Service para operações lógicas que englobam as Regras de Negócio.
     * Injetado automaticamente pelo Spring através de {@code @Autowired}.
     */
    @Autowired
    private TaskService service;

    /**
     * Endpoint para criação de uma nova tarefa.
     * <p>
     * Delega a validação dos dados de entrada ao Bean Validation ({@code @Valid})
     * e toda a lógica de criação ao {@link TaskService}. Retorna HTTP 201 (Created)
     * com o DTO da tarefa criada no corpo da resposta.
     * </p>
     *
     * @param requestDTO DTO validado contendo os dados da tarefa
     * @param request    Requisição HTTP contendo o userId injetado pelo filtro de autenticação
     * @return ResponseEntity com status 201 e o DTO da tarefa criada
     */
    @Operation(
            summary = "Cria uma nova tarefa",
            description = "Cria uma tarefa para o usuário autenticado. Requer título, datas válidas e prioridade."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Tarefa criada com sucesso"
    )

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody TaskRequestDTO requestDTO, HttpServletRequest request) {

        var userId = (UUID) request.getAttribute("userId");

        TaskResponseDTO responseDTO = service.create(requestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Endpoint para listagem de tarefas do usuário autenticado.
     * <p>
     * Delega a busca ao {@link TaskService}, que filtra as tarefas pelo userId
     * extraído do filtro de autenticação. Retorna HTTP 200 (OK) com a lista de tarefas.
     * </p>
     *
     * @param request Requisição HTTP contendo o userId injetado pelo filtro de autenticação
     * @return Lista de DTOs das tarefas do usuário
     */
    @Operation(
            summary = "Lista tarefas do usuário",
            description = "Retorna todas as tarefas pertencentes ao usuário autenticado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de tarefas retornada com sucesso"
    )
    @GetMapping("/list")
    public List<TaskResponseDTO> list(HttpServletRequest request) {

        var userId = (UUID) request.getAttribute("userId");

        return service.list(userId);
    }

    /**
     * Endpoint para atualização de uma tarefa existente.
     * <p>
     * Delega a validação de propriedade, regras de negócio e atualização ao {@link TaskService}.
     * Permite atualização parcial (apenas os campos fornecidos são alterados).
     * Retorna HTTP 200 (OK) com o DTO da tarefa atualizada.
     * </p>
     *
     * @param requestDTO DTO validado contendo os dados a serem atualizados
     * @param request    Requisição HTTP contendo o userId injetado pelo filtro de autenticação
     * @param id         Identificador da tarefa a ser atualizada
     * @return ResponseEntity com status 200 e o DTO da tarefa atualizada
     */
    @Operation(
            summary = "Atualiza uma tarefa",
            description = "Atualiza uma tarefa existente do usuário autenticado. Apenas os campos fornecidos serão alterados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tarefa atualizada com sucesso"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody TaskRequestDTO requestDTO, HttpServletRequest request, @PathVariable UUID id) {

        var userId = (UUID) request.getAttribute("userId");

        TaskResponseDTO responseDTO = service.update(id, requestDTO, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
