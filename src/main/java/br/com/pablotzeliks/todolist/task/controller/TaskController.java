package br.com.pablotzeliks.todolist.task.controller;

import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository repository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {

        task.setUserId((UUID) request.getAttribute("userId"));

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial / final inválida.");
        } else if (task.getStartAt().isAfter(task.getEndAt()) || task.getStartAt().isEqual(task.getEndAt())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial não pode ser posterior ou Igual a Data Final.");
        }

        var newTask = repository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(newTask);
    }
}
