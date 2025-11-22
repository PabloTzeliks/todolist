package br.com.pablotzeliks.todolist.user.repository;

import br.com.pablotzeliks.todolist.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}
