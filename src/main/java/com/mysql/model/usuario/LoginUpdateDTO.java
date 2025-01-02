package com.mysql.model.usuario;

public record LoginUpdateDTO(
        String email,
        String senha
) {
}
