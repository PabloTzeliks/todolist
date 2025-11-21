package br.com.pablotzeliks.todolist.user.controller;

import br.com.pablotzeliks.todolist.user.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    // Endpoint to create a new user

    @PostMapping("/create")
    public void create(@RequestBody User user) {

        System.out.println("Creating user: " + user.getUsername());
    }
}
