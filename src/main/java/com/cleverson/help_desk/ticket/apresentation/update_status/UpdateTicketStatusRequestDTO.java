package com.cleverson.help_desk.ticket.apresentation.update_status;

import com.cleverson.help_desk.ticket.domain.TicketStatus;

public record UpdateTicketStatusRequestDTO(
        TicketStatus status
) {
}
