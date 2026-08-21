package com.cleverson.help_desk.service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateServiceInput(
        UUID id,
        String title,
        String description,
        BigDecimal price
) {
}
