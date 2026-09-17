package com.cleverson.help_desk.ticket.application.dto;

import java.math.BigDecimal;

public record CreateAdditionalServiceInput(
        String description,
        BigDecimal price
) {}