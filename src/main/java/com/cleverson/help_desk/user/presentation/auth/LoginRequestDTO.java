package com.cleverson.help_desk.user.presentation.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password
) {
}
