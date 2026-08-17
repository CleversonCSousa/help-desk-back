package com.cleverson.help_desk.customer.presentation.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is mandatory")
        @Size(min = 12, message = "Password must be at least 8 characters")
        String password
) {
}
