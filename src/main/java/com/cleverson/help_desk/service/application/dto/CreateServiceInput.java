package com.cleverson.help_desk.service.application.dto;

import java.math.BigDecimal;

public record CreateServiceInput(
        String title,
        String description,
        BigDecimal price
) {
}
