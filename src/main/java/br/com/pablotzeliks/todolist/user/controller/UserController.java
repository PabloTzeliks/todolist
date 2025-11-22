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

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository repository;

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
