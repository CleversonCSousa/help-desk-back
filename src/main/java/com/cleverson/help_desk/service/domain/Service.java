package com.cleverson.help_desk.service.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Service(
        UUID id,
        String title,
        String description,
        BigDecimal price,
        Boolean isActive,
        LocalDateTime createdAt
) {
}