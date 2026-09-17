package com.cleverson.help_desk.ticket.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAdditionalServiceResponse(
        UUID id,
        String description,
        BigDecimal price
) {}