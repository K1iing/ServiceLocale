package com.mysql.model.email;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(
        @NotBlank
        String token,
        @NotBlank
        String email,
        @NotBlank
        String newPassword

) {
}
