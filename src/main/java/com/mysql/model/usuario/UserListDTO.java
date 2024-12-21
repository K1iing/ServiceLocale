package com.mysql.model.usuario;

public record UserListDTO(
        Long id,
        String email,
        String password
) {
}
