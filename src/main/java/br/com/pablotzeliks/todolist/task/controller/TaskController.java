package br.com.pablotzeliks.todolist.task.controller;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST responsável pelo gerenciamento de tarefas.
 * <p>
 * Esta classe expõe endpoints para operações CRUD de tarefas, incluindo
 * criação, listagem e atualização. Todos os endpoints são prefixados com {@code /tasks}
 * e requerem autenticação via Basic Auth, que é validada pelo filtro {@link br.com.pablotzeliks.todolist.security.FilterTaskAuth}.
 * </p>
 * <p>
 * A anotação {@code @RestController} combina {@code @Controller} e {@code @ResponseBody},
 * indicando que os métodos retornam dados diretamente no corpo da resposta HTTP.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see Task
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

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody TaskRequestDTO requestDTO, HttpServletRequest request) {

        var userId = (UUID) request.getAttribute("userId");

        try {

            TaskResponseDTO responseDTO = service.create(requestDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/list")
    public List<TaskResponseDTO> list(HttpServletRequest request) {

        var userId = (UUID) request.getAttribute("userId");

        return service.list(userId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody TaskRequestDTO requestDTO, HttpServletRequest request, @PathVariable UUID id) {

        var userId = (UUID) request.getAttribute("userId");

        try {

            TaskResponseDTO responseDTO = service.update(id, requestDTO, userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
