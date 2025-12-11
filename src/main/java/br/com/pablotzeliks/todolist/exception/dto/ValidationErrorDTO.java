package br.com.pablotzeliks.todolist.exception.dto;

public record ValidationErrorDTO(

        String field,
        String message
) { }