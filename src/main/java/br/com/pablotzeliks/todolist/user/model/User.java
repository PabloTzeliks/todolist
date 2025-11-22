package br.com.pablotzeliks.todolist.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name ="tb_user")
public class User {

    @Id
    @GeneratedValue(generator ="UUID")
    private UUID id;

    private String name;
    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
