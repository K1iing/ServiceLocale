package com.mysql.model.profissional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfissionalDTO(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,
        @NotNull(message = "A especialidade não pode ser nula")
        Especialidade especialidade) {
}
