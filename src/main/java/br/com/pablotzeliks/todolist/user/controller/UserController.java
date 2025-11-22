package br.com.pablotzeliks.todolist.user.controller;

import br.com.pablotzeliks.todolist.user.model.User;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository repository;

    @PostMapping("/create")
    public User create(@RequestBody User user) {

        return this.repository.save(user);
    }
}
