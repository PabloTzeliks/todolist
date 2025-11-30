package br.com.pablotzeliks.todolist.task.controller;

import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import br.com.pablotzeliks.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST responsável pelo gerenciamento de tarefas.
 * <p>
 * Esta classe expõe endpoints para operações CRUD de tarefas, incluindo
 * criação, listagem e atualização. Todos os endpoints são prefixados com {@code /tasks}
 * e requerem autenticação via Basic Auth, que é validada pelo filtro {@link br.com.pablotzeliks.todolist.filter.FilterTaskAuth}.
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
 * @see ITaskRepository
 * @see br.com.pablotzeliks.todolist.filter.FilterTaskAuth
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    /**
     * Repositório para operações de persistência de tarefas.
     * Injetado automaticamente pelo Spring através de {@code @Autowired}.
     */
    @Autowired
    private ITaskRepository repository;

    /**
     * Cria uma nova tarefa para o usuário autenticado.
     * <p>
     * Este endpoint realiza as seguintes validações antes de criar a tarefa:
     * </p>
     * <ul>
     *   <li>A data de início deve ser posterior à data atual.</li>
     *   <li>A data de término deve ser posterior à data atual.</li>
     *   <li>A data de início deve ser anterior à data de término.</li>
     *   <li>A data de início não pode ser igual à data de término.</li>
     * </ul>
     *
     * @param task objeto contendo os dados da tarefa a ser criada
     * @param request objeto da requisição HTTP contendo o userId do usuário autenticado
     * @return {@link ResponseEntity} contendo:
     *         <ul>
     *           <li>HTTP 200 (OK) com a tarefa criada em caso de sucesso</li>
     *           <li>HTTP 400 (Bad Request) se as datas forem inválidas</li>
     *         </ul>
     */
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {

        var currentDate = LocalDateTime.now();
        var userId = (UUID) request.getAttribute("userId");

        task.setUserId(userId);

        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial / final inválida.");
        } else if (task.getStartAt().isAfter(task.getEndAt()) || task.getStartAt().isEqual(task.getEndAt())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial não pode ser posterior ou Igual a Data Final.");
        }

        var newTask = repository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(newTask);
    }

    /**
     * Lista todas as tarefas do usuário autenticado.
     * <p>
     * Retorna uma lista contendo todas as tarefas pertencentes ao usuário
     * identificado pelo userId presente no atributo da requisição.
     * </p>
     *
     * @param request objeto da requisição HTTP contendo o userId do usuário autenticado
     * @return lista de tarefas do usuário, ou lista vazia se não houver tarefas
     */
    @GetMapping("/list")
    public List<Task> list(HttpServletRequest request) {

        var userId = request.getAttribute("userId");

        return repository.findByUserId((UUID) userId);
    }

    /**
     * Atualiza uma tarefa existente do usuário autenticado.
     * <p>
     * Este endpoint permite a atualização parcial de uma tarefa, copiando apenas
     * as propriedades não nulas do objeto recebido para a tarefa existente no banco.
     * Também atualiza automaticamente o campo {@code updatedAt} com a data/hora atual.
     * </p>
     * <p>
     * A tarefa só pode ser atualizada se pertencer ao usuário autenticado.
     * </p>
     *
     * @param task objeto contendo os campos a serem atualizados (campos nulos são ignorados)
     * @param request objeto da requisição HTTP contendo o userId do usuário autenticado
     * @param id identificador único da tarefa a ser atualizada
     * @return {@link ResponseEntity} contendo:
     *         <ul>
     *           <li>HTTP 200 (OK) com a tarefa atualizada em caso de sucesso</li>
     *           <li>HTTP 404 (Not Found) se a tarefa não existir ou não pertencer ao usuário</li>
     *         </ul>
     */
    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody Task task, HttpServletRequest request, @PathVariable UUID id) {

        var updateTime = LocalDateTime.now();
        var userId = (UUID) request.getAttribute("userId");

        var taskFromDb = repository.findByIdAndUserId(id, userId);

        if (taskFromDb == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
        }

        Utils.copyNonNullProperties(task, taskFromDb);

        taskFromDb.setUpdatedAt(updateTime);
        var updatedTask = repository.save(taskFromDb);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }
}
