package com.mysql.model.email;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(
        @NotBlank
        TokenDTO token,
        @NotBlank
        String email,
        @NotBlank
        String newPassword

) {
}
