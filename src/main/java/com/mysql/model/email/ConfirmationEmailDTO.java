package com.mysql.model.email;

import jakarta.validation.constraints.NotBlank;

public record ConfirmationEmailDTO(
        @NotBlank
        String email,
        @NotBlank
        String token
) {
}
