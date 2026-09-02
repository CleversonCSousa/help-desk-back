package com.cleverson.help_desk.ticket.apresentation.create;

import com.cleverson.help_desk.ticket.domain.Ticket;

public record CreateTicketResponseDTO(
        String message,
        Ticket ticket
) {
}
