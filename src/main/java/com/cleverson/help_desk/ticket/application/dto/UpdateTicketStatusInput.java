package com.cleverson.help_desk.ticket.application.dto;

import com.cleverson.help_desk.ticket.domain.TicketStatus;

import java.util.UUID;

public record UpdateTicketStatusInput(
        UUID ticketId,
        UUID userId,
        TicketStatus status
) {
}