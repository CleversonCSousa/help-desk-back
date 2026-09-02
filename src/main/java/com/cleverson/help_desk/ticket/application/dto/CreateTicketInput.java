package com.cleverson.help_desk.ticket.application.dto;

import java.util.UUID;

public record CreateTicketInput(
        UUID customerId,
        String title,
        String description,
        UUID serviceId
) {
}
