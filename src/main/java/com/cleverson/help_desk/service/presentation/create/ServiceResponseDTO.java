package com.cleverson.help_desk.service.presentation.create;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceResponseDTO(
        UUID id,
        String title,
        BigDecimal price
) {
}
