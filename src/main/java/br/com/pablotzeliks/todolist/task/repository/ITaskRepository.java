package br.com.pablotzeliks.todolist.task.repository;

import br.com.pablotzeliks.todolist.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUserId(UUID userId);
}
