package com.cleverson.help_desk.ticket.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Ticket(
        UUID id,
        Integer code,
        String title,
        String description,
        BigDecimal basePrice,
        TicketStatus status,

        UUID customerId,
        UUID serviceId,
        UUID technicianId,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
