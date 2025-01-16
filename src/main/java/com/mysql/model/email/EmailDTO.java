package com.mysql.model.email;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank
        String email
) {
}
