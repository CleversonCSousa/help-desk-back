package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.ListTicketsInput;
import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.application.exceptions.UnauthorizedTicketAccessException;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListTicketsUseCase {
    private final TicketQueryGateway ticketQueryGateway;

    public ListTicketsUseCase(TicketQueryGateway ticketQueryGateway) {
        this.ticketQueryGateway = ticketQueryGateway;
    }

    public Page<TicketSummaryResponse> execute(ListTicketsInput input) {
        if (input.role() == UserRole.ADMIN) {
            return this.ticketQueryGateway.findAllSummaries(input.pageable());
        }

        if (input.role() == UserRole.TECHNICIAN) {
            return this.ticketQueryGateway.findAllSummariesByTechnicianId(input.userId(), input.pageable());
        }

        if (input.role() == UserRole.CUSTOMER) {
            return this.ticketQueryGateway.findAllSummariesByCustomerId(input.userId(), input.pageable());
        }

        throw new UnauthorizedTicketAccessException();
    }
}
