package com.cleverson.help_desk.ticket.apresentation.update_status;

import com.cleverson.help_desk.ticket.domain.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketStatusRequest(
        @NotNull(message = "Status is required")
        TicketStatus status
) {
}
