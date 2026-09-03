package com.cleverson.help_desk.ticket.application.dto;

import com.cleverson.help_desk.ticket.domain.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketSummaryResponse(
        UUID id,
        Integer code,
        String title,
        String serviceName,
        BigDecimal totalPrice,
        String customerName,
        String technicianName,
        TicketStatus status,
        LocalDateTime updatedAt
) {}