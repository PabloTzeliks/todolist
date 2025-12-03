package br.com.pablotzeliks.todolist.user.dto;

public record UserRequestDTO(

        String name,
        String username,
        String password
) { }