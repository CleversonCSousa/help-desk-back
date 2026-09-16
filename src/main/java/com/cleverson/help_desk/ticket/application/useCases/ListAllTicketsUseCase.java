package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListAllTicketsUseCase {
    private final TicketQueryGateway ticketQueryGateway;

    public ListAllTicketsUseCase(TicketQueryGateway ticketQueryGateway) {
        this.ticketQueryGateway = ticketQueryGateway;
    }

    public Page<TicketSummaryResponse> execute(Pageable pageable) {
        return this.ticketQueryGateway.findAllSummaries(pageable);
    }
}
