package com.cleverson.help_desk.user.presentation.auth;

import com.cleverson.help_desk.customer.presentation.register.UserResponseDTO;

public record LoginResponseDTO(UserResponseDTO user, String accessToken) {
}
