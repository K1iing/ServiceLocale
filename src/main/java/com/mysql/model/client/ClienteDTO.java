package com.mysql.model.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,
        @NotBlank(message = "O email não pode ser vazio")
        @Email(message = "Insira um email valido")
        String email,
        @NotBlank(message = "O telefone não pode ser vazio")
        String telefone) {
}
