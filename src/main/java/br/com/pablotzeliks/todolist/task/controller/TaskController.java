package br.com.pablotzeliks.todolist.task.controller;

import br.com.pablotzeliks.todolist.task.model.Task;
import br.com.pablotzeliks.todolist.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository repository;

    @PostMapping("/create")
    public Task create(@RequestBody Task task) {

        return repository.save(task);
    }
}
