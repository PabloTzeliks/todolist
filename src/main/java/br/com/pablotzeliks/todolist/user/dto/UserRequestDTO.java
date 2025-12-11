package br.com.pablotzeliks.todolist.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "You must insert a name")
        String name,

        @NotBlank(message = "You must insert a username")
        String username,

        @NotBlank(message = "You must insert a Password")
        @Size(min = 6, max = 20, message = "The password must have between 6 and 20 characters")
        String password
) { }