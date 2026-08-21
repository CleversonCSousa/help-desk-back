package com.cleverson.help_desk.customer.presentation.list;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String email,
        String avatarUrl
) {
}
