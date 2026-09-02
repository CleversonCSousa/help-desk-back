package com.cleverson.help_desk.ticket.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketAdditionalService(
        UUID id,
        UUID ticketId,
        UUID serviceId,
        String description,
        BigDecimal price
) {
}
